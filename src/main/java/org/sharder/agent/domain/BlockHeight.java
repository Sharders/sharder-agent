package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <DESC HERE>
 *
 * @author bubai
 * @date 2018/4/8
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockHeight {

    private int height;

    @JsonProperty(value = "height")
    public int getHeight() {
        return height;
    }

    @JsonProperty(value = "numberOfBlocks")
    public void setHeight(int height) {
        this.height = height - 1;
    }
}
