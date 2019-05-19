package com.secondhand.tradingplatformadmincontroller.controller.front.personal;

import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.UserService;
import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
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
    private UserService userService;

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

        //设置codePay的路由(例子)
        //https://codepay.fateqq.com/creat_order/?price=10&pay_id=%E4%B8%8A%E8%BF%87%E5%BA%A6%E7%9A%84%E5%8F%91%E6%9D%A1&type=1&token=NjAyshrrB0LcKeNTttJPBilVekuIDudP&act=0&id=221429&debug=1&pay_type=1&notify_url=&return_url=
        //http://localhost:8006/front/payment/codePay.html?price=0.01&type=1&pay_id=1&param=
        //更改成您的token令牌
        String token = "NjAyshrrB0LcKeNTttJPBilVekuIDudP";
        //更改成您的码支付ID
        String codePayId = "221429";
        //http://你的域名/codepay.jsp
        String notifyUrl = "";
        //支付后同步跳转地址
        String returnUrl = "";

        //判空
        if (price == null) {
            price = "1";
        }
        //参数有中文则需要URL编码
        String url = "http://codepay.fateqq.com:52888/creat_order?id=" + codePayId + "&pay_id=" + payId + "&price=" + price + "&type=" + type + "&token=" + token + "&param=" + param + "&notifyUrl=" + notifyUrl + "&returnUrl=" + returnUrl;

        //跳转
        response.sendRedirect(url);
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

        //拼接url
        //https://codepay.fateqq.com/codepay.html?id=221429&token=NjAyshrrB0LcKeNTttJPBilVekuIDudP&style=1&debug=1
        //更改成您的token令牌
        String token = "NjAyshrrB0LcKeNTttJPBilVekuIDudP";
        //更改成您的码支付ID
        String codePayId = "221429";
        //参数有中文则需要URL编码
        String url = "https://codepay.fateqq.com/codepay.html?id=" + codePayId + "&pay_id=" + payId + "&token=" + token + "&style=1&debug=1";
        //跳转
        response.sendRedirect(url);
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

        //通信密钥
        String key = "Wjg1vmHIVkARHLgwayuSjqJ0kLclh2L5";
        //转为数组
        List<String> keys = new ArrayList<>(params.keySet());
        //重新排序
        Collections.sort(keys);
        String preStr = "";
        //获取接收到的sign 参数
        String sign = params.get("sign");
        //遍历拼接url 拼接成a=1&b=2 进行MD5签名
        for (int i = 0; i < keys.size(); i++) {
            String keyName = keys.get(i);
            String value = params.get(keyName);
            //跳过这些 不签名
            if (ToolUtil.strIsEmpty(value) || keyName.equals("sign")) {
                continue;
            }
            if (ToolUtil.strIsEmpty(preStr)) {
                preStr = keyName + "=" + value;
            } else {
                preStr = preStr + "&" + keyName + "=" + value;
            }
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((preStr + key).getBytes());
        String mySign = new BigInteger(1, md.digest()).toString(16).toLowerCase();
        if (mySign.length() != 32) {
            mySign = "0" + mySign;
        }
        if (mySign.equals(sign)) {

            //给该用户充值
            //先找用户名
            String userName = params.get("pay_id");
            User user = userService.mySelectUserByUserName(userName);
            if (ToolUtil.objIsEmpty(user)){
                //找充了多少钱
                Float price = Float.parseFloat(params.get("price"));
                user.setBalance(user.getBalance() + price);
                userService.myUpdateById(user);
            }
            return "ok";
        } else {
            //参数不合法
            return "fail";
        }
    }
}