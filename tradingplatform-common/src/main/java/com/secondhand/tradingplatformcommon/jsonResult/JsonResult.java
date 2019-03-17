package com.secondhand.tradingplatformcommon.jsonResult;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author 81079
 */

@JsonInclude(value = Include.NON_NULL)
public class JsonResult<T> extends Result {

    private static final long serialVersionUID = 6308138259545240612L;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JsonResult() {
        super();
    }

    /**
     * 返回数据，消息和结果
     *
     * @param data
     * @param message
     * @param success
     */
    public JsonResult(T data, String message, boolean success) {
        this.data = data;
        super.setMessage(message);
        super.setSuccess(success);
    }

    /**
     * 返回数据和消息
     *
     * @param data
     * @param message
     */
    public JsonResult(T data, String message) {
        this.data = data;
        super.setMessage(message);
    }

    /**
     * 返回数据
     *
     * @param data
     */
    public JsonResult(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "jsonResult{" +
                "data=" + data +
                '}';
    }
}
