package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Peer
 *
 * @author bubai
 * @author xy
 * @date 2018/4/13
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Peer {
    private String address;
    private String uri;
    private int type;
    private PeerLoad peerLoad;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Peer peer = (Peer) o;

        return address.equals(peer.address);
    }

    public PeerLoad getPeerLoad() {
        return peerLoad;
    }

    public void setPeerLoad(PeerLoad peerLoad) {
        this.peerLoad = peerLoad;
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
