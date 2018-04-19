package org.sharder.agent.domain;

/**
 * Peer
 *
 * @author bubai
 * @date 2018/4/13
 */
public class Peer {
    private String address;
    private int type;

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

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
