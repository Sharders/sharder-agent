package org.sharder.agent.utils;

import okhttp3.Response;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
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

    private static Peer bestPeer;
    private static int minLoad = 5;
    private static int maxLoad = 10;
    static private Map<PeerState,HashSet<Peer>> peerMap = new ConcurrentHashMap<>();

    @Value("${chain_admin_pwd}")
    private String CHAIN_ADMIN_PWD;
    // bestPeer commercialUris
    // TODO it should save in database
    static public Map<String,HashSet<String>> commercialMap = new ConcurrentHashMap<>();
    static {
        peerMap.put(PeerState.CHECKED,new HashSet<>());
        peerMap.put(PeerState.UNCHECKED,new HashSet<>());
        peerMap.put(PeerState.ACTIVED,new HashSet<>());
    }

    public Peer getBestPeer(){
        try{
            if(bestPeer == null) {
                Random random = new Random();
                Peer peer = peersConfig.getList().get(random.nextInt(peersConfig.getList().size()));
                peer = getPeerInfo(peer);
                bestPeer = praseStringToPeer(peer.getBestPeer());
            }
        }catch (Exception e){
            logger.error("can't get peer info :", e);
            getBestPeer();
        }

        try{
            getPeerInfo(bestPeer);
        }catch (Exception e){
            logger.error("can't get bestPeer info :", e);
            String addr = bestPeer.getUri();
            bestPeer = null;
            getBestPeer();
            notifyCommercials(addr);
        }

        return bestPeer;
    }

    /**
     *
     * @param peer error peer
     * @return
     */
    private boolean notifyCommercials(String peer){
        boolean result = true;
        getBestPeer();
        Peer curBest = bestPeer;
        HashSet<String> commercials = commercialMap.get(peer);
        for(String commercial : commercials){
            HashMap<String,String> params = new HashMap<>();
            params.put("requestType", RequestType.UPDATE_PEER.getType());
            params.put("newPeer", curBest.getUri());
            params.put(RequestManager.KEY_BASE_URL,commercial);
            try{
                Response response = requestManager.requestSyn(RequestManager.TYPE_POST, params);
                if(response.isSuccessful()){
                    String responseStr = response.body().string();
                    // TODO failed notify again
                    if(!responseStr.contains(bestPeer.getUri())){
                        logger.error("notify " + commercial + "failed, response is " + responseStr);
                        result = false;
                        continue;
                    }
                    //update map
                    if(commercialMap.containsKey(curBest.getUri())){
                        commercialMap.get(curBest.getUri()).add(commercial);
                    }else {
                        HashSet<String> newCommercials = new HashSet();
                        newCommercials.add(commercial);
                        commercialMap.put(bestPeer.getUri(),newCommercials);
                    }
                }
            }catch (Exception e){
                logger.error("notify " + commercial + "failed" ,e);
                result = false;
            }
        }
        commercialMap.remove(peer);
        return result;
    }

    private Peer getPeerInfo(Peer peer) throws Exception{
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType", RequestType.GET_INFO.getType());
        params.put("adminPassword", CHAIN_ADMIN_PWD);
        params.put(RequestManager.KEY_BASE_URL, peer.getUri());
        params.put(RequestManager.KEY_ACTION_URL, ACTION_URL_SHARDER);

        Peer remote = ResponseUtils.convert(requestManager.requestSyn(RequestManager.TYPE_POST, params), Peer.class);
        remote.setBestPeer(remote.getBestPeer().substring(7));
        if(remote.getBestPeer().contains("127.0.0.1")){
            String addr = remote.getBestPeer().replace("127.0.0.1",peer.getAddress());
            remote.setBestPeer(addr);
        }
        peer.setPeerLoad(remote.getPeerLoad());
        peer.setBestPeer(remote.getBestPeer());
        return peer;
    }

    /**
     * Syn peers from Sharder Chain
     *
     * @return the best peer
     */
    @Scheduled(initialDelay=1000, fixedDelay=20000)
    private Peer synPeers() {
        //TODO only run three times when system start
        getBestPeer();

        for (String key : commercialMap.keySet()) {
            Peer peer = praseStringToPeer(key);
            try{
                getPeerInfo(peer);
            }catch (Exception e){
                logger.error("error with get info from peer" + peer.getAddress() ,e);
                notifyCommercials(key);
            }
            int load = peer.getPeerLoad().getLoad();
            if(load > maxLoad)
                notifyCommercials(key);
        }

        return bestPeer;
    }

    private Peer praseStringToPeer(String addr){
        Peer peer = new Peer();
        peer.setAddress(addr.substring(0,addr.indexOf(":")));
        if(addr.contains("http://"))
            peer.setUri(addr);
        else
            peer.setUri("http://" + addr);
        return peer;
    }
}
