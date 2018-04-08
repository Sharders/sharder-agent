package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;

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
    private String index;
    private BigDecimal amount;
    private BigDecimal fee;
    private String sender;
    private String recipient;
    private BigInteger height;
    private BigInteger timestamp;

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
    public String getIndex() {
        return index;
    }

    @JsonProperty(value = "transactionIndex")
    public void setIndex(String index) {
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
        this.fee = fee.divide(BigDecimal.valueOf(100000000L));;
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

    public BigInteger getHeight() {
        return height;
    }

    public void setHeight(BigInteger height) {
        this.height = height;
    }

    public BigInteger getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(BigInteger timestamp) {
        this.timestamp = timestamp;
    }
}
