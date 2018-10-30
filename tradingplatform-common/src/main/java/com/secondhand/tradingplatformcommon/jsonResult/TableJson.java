package com.secondhand.tradingplatformcommon.jsonResult;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author 81079
 */

public class TableJson<T> {

    /**
     * 总记录数
     */
    private Long recordsTotal;

    /**
     * 具体的数据对象数组
     */
    private List<T> data;

    /**
     * 返回成功信息
     */
    private Boolean success;

    /**
     * 服务器出了问题后的友好提示
     */
    @ApiModelProperty("服务器出了问题后的友好提示")
    private String message;

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
