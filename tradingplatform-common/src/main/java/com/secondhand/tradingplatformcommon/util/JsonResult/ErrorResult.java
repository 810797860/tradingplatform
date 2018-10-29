package com.secondhand.tradingplatformcommon.util.JsonResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 81079
 */

public class ErrorResult extends Result {

    private static final long serialVersionUID = -874458946549289108L;

    /**
     * 封装多个 错误信息
     */
    private Map<String, Object> errors = new HashMap<>();

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

    public ErrorResult() {
    }

}
