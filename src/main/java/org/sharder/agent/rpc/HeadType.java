package org.sharder.agent.rpc;

/**
 * HeadType
 *
 * @author bubai
 * @date 2018/3/26
 */
public enum HeadType {
    Content_Type("application/json"),
    Accept("application/json");

    private String value;

    private HeadType(String type) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
