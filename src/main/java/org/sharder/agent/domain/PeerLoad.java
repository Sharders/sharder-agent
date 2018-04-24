package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author x.y
 * @date 2018/4/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeerLoad {
    private String host;
    private int port;
    private String uri;
    private int load = -1;
    private long lastUpdate = -1;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}