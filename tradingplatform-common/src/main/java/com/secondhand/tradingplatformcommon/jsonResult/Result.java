package com.secondhand.tradingplatformcommon.jsonResult;

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
    private int code;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
