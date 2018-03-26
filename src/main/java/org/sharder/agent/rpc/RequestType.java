package org.sharder.agent.rpc;

/**
 * <DESC HERE>
 *
 * @author bubai
 * @date 2018/3/26
 */
public enum RequestType {
    GET_ACCOUNT_ID("getAccountId");

    private String type;

    private RequestType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
