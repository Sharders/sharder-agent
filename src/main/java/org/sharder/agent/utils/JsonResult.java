package org.sharder.agent.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * JsonResult
 * @author bubai
 * @date 2018/3/19
 */
@ApiModel(description= "返回响应数据")
public class JsonResult {

    /**
     * return status
     */
    @ApiModelProperty(value = "返回状态")
    private String status = null;

    /**
     * return result
     */
    @ApiModelProperty(value = "返回结果")
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
