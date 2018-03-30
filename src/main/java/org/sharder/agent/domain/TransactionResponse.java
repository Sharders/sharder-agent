package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * TransactionResponse
 *
 * @author bubai
 * @date 2018/3/26
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponse {

    private String transaction;
    private String fullHash;
    private TransactionJSON transactionJSON;

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getFullHash() {
        return fullHash;
    }

    public void setFullHash(String fullHash) {
        this.fullHash = fullHash;
    }

    public TransactionJSON getTransactionJSON() {
        return transactionJSON;
    }

    public void setTransactionJSON(TransactionJSON transactionJSON) {
        this.transactionJSON = transactionJSON;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    protected static class TransactionJSON {
        private String senderPublicKey;
        private String feeNQT;
        private String senderRS;

        public String getSenderPublicKey() {
            return senderPublicKey;
        }

        public void setSenderPublicKey(String senderPublicKey) {
            this.senderPublicKey = senderPublicKey;
        }

        public String getFeeNQT() {
            return feeNQT;
        }

        public void setFeeNQT(String feeNQT) {
            this.feeNQT = feeNQT;
        }

        public String getSenderRS() {
            return senderRS;
        }

        public void setSenderRS(String senderRS) {
            this.senderRS = senderRS;
        }
    }
}
