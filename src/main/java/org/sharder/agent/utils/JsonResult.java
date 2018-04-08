package org.sharder.agent.utils;

/**
 * JsonResult
 * @author bubai
 * @date 2018/3/19
 */
public class JsonResult<T> {

    /**
     * return status
     */
    private String status = null;

    /**
     * return result
     */
    private T result = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
