package com.secondhand.tradingplatformadmincontroller.controller.admin.business;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.secondhand.tradingplatformadminservice.service.admin.business.ShortMessageService;
import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : zhangjk
 * @description : 测试用的控制器
 * @since : Create in 2018-11-11
 */
@Controller("adminMyTestController")
@Api(value = "/admin/test", description = "测试用的控制器")
@RequestMapping("/admin/test")
public class MyTestController extends BaseController{

    @Autowired
    private ShortMessageService shortMessageService;

    /**
     * @description : 测试阿里云发送邮件
     * @author : zhangjk
     * @since : Create in 2019-03-21
     */
    @GetMapping(value = "/myTestSendEmail", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/myTestSendEmail", notes = "测试阿里云发送邮件")
    @ResponseBody
    public JsonResult<SendSmsResponse> myTestSendEmail(@ApiParam(name = "httpServletRequest", value = "服务器请求") HttpServletRequest httpServletRequest) throws ClientException {
        JsonResult<SendSmsResponse> resJson = new JsonResult<>();
        SendSmsResponse sendSmsResponse = shortMessageService.sendVerificationCode(httpServletRequest, "13652288353");resJson.setData(sendSmsResponse);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setSuccess(true);
        return resJson;
    }
}
