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
    UPLOAD_DATA("uploadTaggedData"),
    GET_BLOCKS("getBlocks"),
    GET_ACCOUNT_TRANSACTIONS("getBlockchainTransactions"),
    GET_BLOCK_STATUS("getBlockchainStatus"),
    GET_TRANSACTION("getTransaction"),
    GET_BLOCK("getBlock"),
    GET_CONSTANTS("getConstants"),
    GET_ACCOUNT("getAccount"),
    GET_PEERS("getPeers"),
    GET_DATA("getTaggedData"),
    GET_INFO("getInfo"),
    UPDATE_PEER("updatePeer");


    private String type;
    private RequestType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
}
