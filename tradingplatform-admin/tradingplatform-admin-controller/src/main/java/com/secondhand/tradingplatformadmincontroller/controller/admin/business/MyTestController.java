package com.secondhand.tradingplatformadmincontroller.controller.admin.business;

import com.aliyuncs.exceptions.ClientException;
import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zhangjk
 * @description : 测试用的控制器
 * @since : Create in 2018-11-11
 */
@Controller("adminMyTestController")
@Api(value = "/admin/test", description = "测试用的控制器")
@RequestMapping("/admin/test")
public class MyTestController extends BaseController{

    /**
     * @description : 测试阿里云发送邮件
     * @author : zhangjk
     * @since : Create in 2019-03-21
     */
    @GetMapping(value = "/myTestSendEmail", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/myTestSendEmail", notes = "测试阿里云发送邮件")
    @ResponseBody
    public JsonResult myTestSendEmail() throws ClientException {
        JsonResult resJson = new JsonResult();
        ToolUtil.testSendEmail();
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setSuccess(true);
        return resJson;
    }
}
