package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Transaction
 *
 * @author bubai
 * @date 2018/4/2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private String transactionId;
    private String hash;
    private int type;
    private int index;
    private BigDecimal amount;
    private BigDecimal fee;
    private String sender;
    private String recipient;
    private int height;
    private long timestamp;
    private int confirmations;

    @JsonProperty(value = "transactionId")
    public String getTransactionId() {
        return transactionId;
    }

    @JsonProperty(value = "transaction")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @JsonProperty(value = "hash")
    public String getHash() {
        return hash;
    }

    @JsonProperty(value = "fullHash")
    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonProperty(value = "index")
    public int getIndex() {
        return index;
    }

    @JsonProperty(value = "transactionIndex")
    public void setIndex(int index) {
        this.index = index;
    }

    @JsonProperty(value = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty(value = "amountNQT")
    public void setAmount(BigDecimal amount) {
        this.amount = amount.divide(BigDecimal.valueOf(100000000L));
    }

    @JsonProperty(value = "fee")
    public BigDecimal getFee() {
        return fee;
    }

    @JsonProperty(value = "feeNQT")
    public void setFee(BigDecimal fee) {
        this.fee = fee.divide(BigDecimal.valueOf(100000000L));
    }

    @JsonProperty(value = "sender")
    public String getSender() {
        return sender;
    }

    @JsonProperty(value = "senderRS")
    public void setSender(String sender) {
        this.sender = sender;
    }

    @JsonProperty(value = "recipient")
    public String getRecipient() {
        return recipient;
    }

    @JsonProperty(value = "recipientRS")
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }
}
