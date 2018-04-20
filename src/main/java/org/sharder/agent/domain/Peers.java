package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Peers
 *
 * @author xy
 * @date 2018/4/20
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Peers {
    private List<Peer> peers;

    public List<Peer> getPeers() {
        return peers;
    }

    public void setPeers(List<Peer> peers) {
        this.peers = peers;
    }
}
