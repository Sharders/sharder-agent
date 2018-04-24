package org.sharder.agent.utils;

import org.sharder.agent.config.PeersConfig;
import org.sharder.agent.controller.PeerController;
import org.sharder.agent.domain.Peer;
import org.sharder.agent.domain.Peers;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PeersHolder
 *
 * @author xy
 * @date 2018/4/19
 */
@Component
@EnableScheduling
public class PeersHolder {
    private static final Logger logger = LoggerFactory.getLogger(PeerController.class);
    @Autowired
    private PeersConfig peersConfig;
    @Autowired
    private RequestManager requestManager;
    private final String ACTION_URL_SHARDER = "sharder";
    enum PeerState {
        CHECKED, UNCHECKED, ACTIVED
    }

    private static Peer bestPeer = null;
    static private Map<PeerState,HashSet<Peer>> peerMap = new ConcurrentHashMap<>();
    static {
        peerMap.put(PeerState.CHECKED,new HashSet<>());
        peerMap.put(PeerState.UNCHECKED,new HashSet<>());
        peerMap.put(PeerState.ACTIVED,new HashSet<>());
    }

    private void synSinglePeers(Peer peer){
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType", RequestType.GET_PEERS.getType());
        params.put("includePeerInfo","true");
        params.put(RequestManager.KEY_BASE_URL, peer.getUri());
        params.put(RequestManager.KEY_ACTION_URL, ACTION_URL_SHARDER);
        try {
            Peers peers = ResponseUtils.convert(requestManager.requestSyn(RequestManager.TYPE_POST, params), Peers.class);

            for(Peer queriedPeer : peers.getPeers()) {
                if(peerMap.get(PeerState.CHECKED).contains(queriedPeer)){
                    findBestPeer(queriedPeer);
                } else{
                    peerMap.get(PeerState.UNCHECKED).add(queriedPeer);
                }
            }

        } catch (Exception e) {
            logger.error("can't syn peers", e);
        }
    }

    private void findBestPeer(Peer peer){
        //TODO ① check load of peer and add peer into ACTIVED map ; ② set the best peer
        bestPeer = bestPeer == null ? peer : (bestPeer.getPeerLoad().getLoad() > peer.getPeerLoad().getLoad() ? bestPeer : peer);
    }

    /**
     * Syn peers from Sharder Chain
     *
     * @return the best peer
     */
    @Scheduled(initialDelay=1000, fixedDelay=20000)
    private Peer synPeers() {
        //TODO only run three times when system start
        for(Peer peer : peersConfig.getList()) synSinglePeers(peer);

        //ping unchecked peers
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType", RequestType.PING.getType());
        params.put(RequestManager.KEY_ACTION_URL, ACTION_URL_SHARDER);
        if(peerMap.get(PeerState.UNCHECKED).size() > 0) {
            for(Peer peer : peerMap.get(PeerState.UNCHECKED)){
                try{
                    String uri = "http://" + peer.getAddress() +":" + peer.getPeerLoad().getPort();
                    peer.setUri(uri);
                    params.put(RequestManager.KEY_BASE_URL, uri);
                    Peer pingPeer = ResponseUtils.convert(requestManager.requestSyn(RequestManager.TYPE_POST, params), Peer.class);
                    peer.getPeerLoad().setLoad(pingPeer.getPeerLoad().getLoad());
                    Date date = new Date();
                    peer.getPeerLoad().setLastUpdate(date.getTime());
                    peerMap.get(PeerState.CHECKED).add(peer);
                    peerMap.get(PeerState.UNCHECKED).remove(peer);

                    synSinglePeers(peer);

                    bestPeer = bestPeer == null ? peer : (bestPeer.getPeerLoad().getLoad() > peer.getPeerLoad().getLoad() ? bestPeer : peer);
                }catch (Exception e){
                    logger.error("can't ping unchecked peer:" + peer.getUri(), e);
                    peerMap.get(PeerState.UNCHECKED).remove(peer);
                }
            }
        }

        //ping checked peers
        for(Peer peer : peerMap.get(PeerState.CHECKED)){
            Date date = new Date();
            if(date.getTime() - peer.getPeerLoad().getLastUpdate() > 3600){
                try{
                    String uri = "http://" + peer.getAddress() +":" + peer.getPeerLoad().getPort();
                    params.put(RequestManager.KEY_BASE_URL, uri);
                    Peer pingPeer = ResponseUtils.convert(requestManager.requestSyn(RequestManager.TYPE_POST, params), Peer.class);
                    peer.setPeerLoad(pingPeer.getPeerLoad());
                    date = new Date();
                    peer.getPeerLoad().setLastUpdate(date.getTime());

                    bestPeer = bestPeer == null ? peer : (bestPeer.getPeerLoad().getLoad() > peer.getPeerLoad().getLoad() ? bestPeer : peer);
                }catch (Exception e){
                    logger.error("can't ping checked peer:" + peer.getUri(), e);
                    peerMap.get(PeerState.UNCHECKED).remove(peer);
                }
            }
        }
        return bestPeer;
    }

    public Peer getBestPeer() {
        return bestPeer == null ? synPeers() : bestPeer;
    }
}
