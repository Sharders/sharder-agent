package org.sharder.agent.controller;

import org.sharder.agent.config.PeersConfig;
import org.sharder.agent.domain.Peer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<Peer>> getdeFinePeer() {
        return ResponseEntity.ok(peersConfig.getList());
    }
}
