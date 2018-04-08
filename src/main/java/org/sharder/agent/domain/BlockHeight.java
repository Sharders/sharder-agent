package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * <DESC HERE>
 *
 * @author bubai
 * @date 2018/4/8
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockHeight {

    private BigInteger height;

    @JsonProperty(value = "height")
    public BigInteger getHeight() {
        return height;
    }

    @JsonProperty(value = "numberOfBlocks")
    public void setHeight(BigInteger height) {
        this.height = height.subtract(BigInteger.ONE);
    }
}
