package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * DataTransactionResponse
 *
 * @author bubai
 * @date 2018/3/26
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTransactionResponse {

    private String transaction;
    private String fullHash;
    private TransactionJSON transactionJSON;

    @JsonProperty(value = "transactionID")
    public String getTransaction() {
        return transaction;
    }

    @JsonProperty(value = "transaction")
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getFullHash() {
        return fullHash;
    }

    public void setFullHash(String fullHash) {
        this.fullHash = fullHash;
    }

    @JsonProperty(value = "transaction")
    public TransactionJSON getTransactionJSON() {
        return transactionJSON;
    }

    @JsonProperty(value = "transactionJSON")
    public void setTransactionJSON(TransactionJSON transactionJSON) {
        this.transactionJSON = transactionJSON;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    protected static class TransactionJSON {
        private String senderPublicKey;
        private BigDecimal fee;
        private String senderID;
        private String senderRS;
        private Attachment attachment;

        public String getSenderPublicKey() {
            return senderPublicKey;
        }

        public void setSenderPublicKey(String senderPublicKey) {
            this.senderPublicKey = senderPublicKey;
        }

        @JsonProperty(value = "fee")
        public BigDecimal getFee() {
            return fee;
        }

        @JsonProperty(value = "feeNQT")
        public void setFee(BigDecimal fee) {
            this.fee = fee.divide(BigDecimal.valueOf(100000000L));;
        }

        @JsonProperty(value = "senderID")
        public String getSenderID() {
            return senderID;
        }
        @JsonProperty(value = "sender")
        public void setSenderID(String senderID) {
            this.senderID = senderID;
        }

        public String getSenderRS() {
            return senderRS;
        }

        public void setSenderRS(String senderRS) {
            this.senderRS = senderRS;
        }

        @JsonProperty(value = "data")
        public Attachment getAttachment() {
            return attachment;
        }

        @JsonProperty(value = "attachment")
        public void setAttachment(Attachment attachment) {
            this.attachment = attachment;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        protected static class Attachment {
            private String hash;
            private String name;
            private String type;

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
