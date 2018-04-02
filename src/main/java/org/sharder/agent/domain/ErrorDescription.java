package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <DESC HERE>
 *
 * @author bubai
 * @date 2018/3/30
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDescription {

    private String errorDescription;
    private int errorCode;

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
