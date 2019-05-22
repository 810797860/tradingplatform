package com.secondhand.tradingplatformadmincontroller.controller.front.personal;

import com.secondhand.tradingplatformadminservice.service.admin.business.PaymentService;
import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author : zhangjk
 * @description : PaymentController 控制器
 * @since : Create in 2019-04-09
 */
@Controller("frontPaymentController")
@Api(value = "/front/payment", description = "PaymentController 控制器")
@RequestMapping("/front/payment")
public class PaymentController extends BaseController {

    @Autowired
    private PaymentService paymentService;

    /**
     * @description : 接收参数，创建订单
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/codePay.html")
    @ApiOperation(value = "/codePay.html", notes = "接收参数，创建订单")
    public void toCodePay(@ApiParam(name = "response", value = "服务器的响应") HttpServletResponse response,
                          @ApiParam(name = "price", value = "表单提交的价格") String price,
                          @ApiParam(name = "type", value = "支付类型  1：支付宝 2：QQ钱包 3：微信") String type,
                          @ApiParam(name = "pay_id", value = "支付人的唯一标识") String payId,
                          @ApiParam(name = "param", value = "自定义一些参数 支付后返回") String param) throws IOException {
        paymentService.toCodePay(response, price, type, payId, param);
    }


    /**
     * @description : 充值页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/recharge.html")
    @ApiOperation(value = "/recharge.html", notes = "充值页面")
    public void toRecharge(@ApiParam(name = "response", value = "服务器的响应") HttpServletResponse response,
                           @ApiParam(name = "pay_id", value = "支付人的唯一标识") String payId) throws IOException {
        paymentService.toRecharge(response, payId);
    }


    /**
     * @description : 验证通知，处理自己的业务
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/notify.html", produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ApiOperation(value = "/notify.html", notes = "验证通知，处理自己的业务")
    @ResponseBody
    public String toNotify(@ApiParam(name = "params", value = "申明hashMap变量储存接收到的参数名用于排序") @RequestParam Map<String, String> params) throws NoSuchAlgorithmException {
        return paymentService.toNotify(params);
    }
}