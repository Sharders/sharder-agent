package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Block
 *
 * @author bubai
 * @date 2018/4/2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "blocks")
public class Block {
    private String blockId;
    private String previousBlockId;
    private int height;
    private int payloadLength;
    private long timestamp;
    private String generator;
    private String generatorRS;
    private ArrayList<Transaction> transactions;
    private BigDecimal totalFee;

    @JsonProperty(value = "blockId", index = 0)
    public String getBlockId() {
        return blockId;
    }

    @JsonProperty(value = "block")
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    @JsonProperty(value = "previousBlockId")
    public String getPreviousBlockId() {
        return previousBlockId;
    }

    @JsonProperty(value = "previousBlock")
    public void setPreviousBlockId(String previousBlockId) {
        this.previousBlockId = previousBlockId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(int payloadLength) {
        this.payloadLength = payloadLength;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getGeneratorRS() {
        return generatorRS;
    }

    public void setGeneratorRS(String generatorRS) {
        this.generatorRS = generatorRS;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @JsonProperty(value = "totalFee")
    public BigDecimal getTotalFee() {
        return totalFee;
    }

    @JsonProperty(value = "totalFeeNQT")
    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee.divide(BigDecimal.valueOf(100000000L));;
    }
}
