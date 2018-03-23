package org.sharder.agent.utils;

import io.swagger.annotations.ApiModel;

/**
 * JsonResult
 * @author bubai
 * @date 2018/3/19
 */
@ApiModel()
public class JsonResult {

    /**
     * return status
     */
    private String status = null;

    /**
     * return result
     */
    private Object result = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
