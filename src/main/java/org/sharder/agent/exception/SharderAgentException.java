package org.sharder.agent.exception;

import org.sharder.agent.domain.ErrorDescription;

/**
 * SharderAgentException
 *
 * @author bubai
 * @date 2018/3/26
 */
public class SharderAgentException extends Exception{
    public SharderAgentException(String message) {
        super(message);
    }
    private ErrorDescription errorDescription;

    public SharderAgentException(ErrorDescription ed) {
        this.errorDescription = ed;
    }

    public ErrorDescription getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(ErrorDescription errorDescription) {
        this.errorDescription = errorDescription;
    }
}
