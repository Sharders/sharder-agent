package org.sharder.agent.controller;

import org.sharder.agent.config.PeersConfig;
import org.sharder.agent.domain.Peer;
import org.sharder.agent.utils.PeersHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * PeerController
 *
 * @author bubai
 * @date 2018/4/13
 */
@RestController
@RequestMapping("/v1/peers")
public class PeerController {
    private static final Logger logger = LoggerFactory.getLogger(PeerController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PeersConfig peersConfig;
    @Autowired
    private PeersHolder peersHolder;

    @GetMapping
    public ResponseEntity<List<Peer>> getRedefinedPeer() {
        return ResponseEntity.ok(peersConfig.getList());
    }

    @GetMapping("/{address}")
    public ResponseEntity<Peer> getPeer(@PathVariable(required = true) String address) {
        HashMap<String, Peer> peersMap = peersConfig.getHashMap();
        Peer peer = peersMap.get(address);
        return ResponseEntity.ok(peer);
    }

    /**
     * Verify the peer whether is valid.
     * @param address address of peer
     * @return true or false
     */
    @GetMapping("/verify/{address}")
    public ResponseEntity<Boolean> verify(@PathVariable(required = true) String address) {
        HashMap<String, Peer> peersMap = peersConfig.getHashMap();
        Peer peer = peersMap.get(address);
        return ResponseEntity.ok(peer == null ? false : true);
    }

    /**
     * The best peer in the peer list.
     * @return
     */
    @GetMapping("/bestPeer")
    public ResponseEntity<String> getBestPeer() {
        String addr = request.getRemoteAddr();
        int port = request.getRemotePort();
        String uri = "http://" + addr + ":" + port;
        Peer bestPeer = peersHolder.getBestPeer();
        if(PeersHolder.commercialMap.containsKey(bestPeer.getUri())){
            PeersHolder.commercialMap.get(bestPeer.getUri()).add(uri);
        }else {
            HashSet<String> commercials = new HashSet();
            commercials.add(uri);
            PeersHolder.commercialMap.put(bestPeer.getUri(),commercials);
        }
        return ResponseEntity.ok(bestPeer.getUri());
    }
}
