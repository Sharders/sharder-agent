package org.sharder.agent.rpc;

/**
 * RequestType Enum
 *
 * @author bubai
 * @date 2018/3/26
 */
public enum RequestType {
    GET_ACCOUNT_ID("getAccountId"),
    SEND_MSG("sendMessage"),
    DOWNLOAD_DATA("downloadTaggedData"),
    UPLOAD_DATA("uploadTaggedData");


    private String type;
    private RequestType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
}
