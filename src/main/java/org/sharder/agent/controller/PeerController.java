package org.sharder.agent.controller;

import org.sharder.agent.config.PeersConfig;
import org.sharder.agent.domain.Peer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private PeersConfig peersConfig;

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
}
