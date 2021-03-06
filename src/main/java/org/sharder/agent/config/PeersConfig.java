package org.sharder.agent.config;

import org.sharder.agent.domain.Peer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * PeersConfig
 *
 * @author bubai
 * @date 2018/4/13
 */
@Component
@ConfigurationProperties(prefix = "peers")
public class PeersConfig {
    private List<Peer> list;

    public List<Peer> getList() {
        return list;
    }

    public void setList(List<Peer> list) {
        this.list = list;
    }

    public HashMap<String, Peer> getHashMap() {
        HashMap<String, Peer> map = new HashMap<>();
        for (Peer i : list) map.put(i.getAddress(),i);
        return map;
    }
}
