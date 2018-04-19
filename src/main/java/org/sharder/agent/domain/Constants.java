package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Block
 *
 * @author bubai
 * @date 2018/4/16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Constants {
    private long epochBeginning;

    public long getEpochBeginning() {
        return epochBeginning;
    }

    public void setEpochBeginning(long epochBeginning) {
        this.epochBeginning = epochBeginning;
    }
}
