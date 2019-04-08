package com.secondhand.tradingplatformadmincontroller.handler;

import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Api(description = "自定义异常拦截器")
public class CustomizeExceptionHandler{

    @ExceptionHandler(CustomizeException.class)
    public JsonResult<CustomizeException> handle(CustomizeException ex){
        JsonResult<CustomizeException> resJson = new JsonResult<>();
        resJson.setSuccess(false);
        resJson.setMessage(ex.getMessage());
        resJson.setData(ex);
        resJson.setCode(ex.getCode());
        return resJson;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public JsonResult<UnauthorizedException> handle(UnauthorizedException ex){
        JsonResult<UnauthorizedException> resJson = new JsonResult<>();
        resJson.setSuccess(false);
        resJson.setMessage(ex.getMessage());
        resJson.setData(ex);
        resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
        return resJson;
    }
}
