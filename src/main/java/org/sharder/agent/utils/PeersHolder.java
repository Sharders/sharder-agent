package org.sharder.agent.utils;

import org.sharder.agent.config.PeersConfig;
import org.sharder.agent.controller.PeerController;
import org.sharder.agent.domain.Peer;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
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

    }

    private static Peer bestPeer = null;
    private Map<PeerState,List<Peer>> peerMap = new ConcurrentHashMap<>();

    private void synSinglePeers(Peer peer){
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType", RequestType.GET_PEERS.getType());
        params.put(RequestManager.KEY_BASE_URL, peer.getUri());
        params.put(RequestManager.KEY_ACTION_URL, ACTION_URL_SHARDER);
        try {
            ResponseUtils.convert(requestManager.requestSyn(RequestManager.TYPE_POST, params), Peer.class);
        } catch (Exception e) {
            logger.error("can't syn peers", e);
        }
    }

    /**
     * Syn peers from Sharder Chain
     *
     * @return the best peer
     */
    private Peer synPeers() {
        for(Peer peer : peersConfig.getList()) synSinglePeers(peer);

        return bestPeer;
    }

    public Peer getBestPeer() {
        return bestPeer == null ? synPeers() : bestPeer;
    }

}
