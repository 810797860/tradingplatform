package com.secondhand.tradingplatformcommon.util.JsonResult;

import java.io.Serializable;

/**
 * @author 81079
 */

public class Result implements Serializable {

    private static final long serialVersionUID = 6022141028443609288L;

    /**
     * 信息
     */
    private String message;

    /**
     * 状态码
     */
    private String statusCode;

    /**
     * 是否成功
     */
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Result() {
    }
}
