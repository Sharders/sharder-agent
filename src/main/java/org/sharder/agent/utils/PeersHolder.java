package org.sharder.agent.utils;

import org.sharder.agent.config.PeersConfig;
import org.sharder.agent.controller.PeerController;
import org.sharder.agent.domain.Peer;
import org.sharder.agent.domain.PeerLoad;
import org.sharder.agent.domain.Peers;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private Map<PeerState,HashSet<Peer>> peerMap = new ConcurrentHashMap<>();

    private void synSinglePeers(Peer peer){
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType", RequestType.GET_PEERS.getType());
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
        PeerLoad peerLoad = peer.getPeerLoad();
        //TODO ① check load of peer and add peer into ACTIVED map ; ② set the best peer
        bestPeer = peer;
    }

    /**
     * Syn peers from Sharder Chain
     *
     * @return the best peer
     */
    private Peer synPeers() {
        for(Peer peer : peersConfig.getList()) synSinglePeers(peer);

        //TODO query and check UNCHECKED peers
        if(peerMap.get(PeerState.UNCHECKED).size() > 0) {

        }

        return bestPeer;
    }

    public Peer getBestPeer() {
        return bestPeer == null ? synPeers() : bestPeer;
    }

}
