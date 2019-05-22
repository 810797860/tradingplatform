package com.secondhand.tradingplatformadmincontroller.serviceimpl.admin.business;

import com.secondhand.tradingplatformadmincontroller.serviceimpl.admin.shiro.UserServiceImpl;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformadminservice.service.admin.business.PaymentService;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : 码支付 服务实现类
 * ---------------------------------
 * @since 2018-12-14
 */
@Service
public class PaymentServiceImpl implements PaymentService{

    @Value("${tradingplatform.payment.token}")
    private String token;

    @Value("${tradingplatform.payment.codePayId}")
    private String codePayId;

    @Value("${tradingplatform.payment.notifyUrl}")
    private String notifyUrl;

    @Value("${tradingplatform.payment.returnUrl}")
    private String returnUrl;

    @Value("${tradingplatform.payment.key}")
    private String key;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public void toCodePay(HttpServletResponse response, String price, String type, String payId, String param) throws IOException {

        //设置codePay的路由(例子)
        //https://codepay.fateqq.com/creat_order/?price=10&pay_id=%E4%B8%8A%E8%BF%87%E5%BA%A6%E7%9A%84%E5%8F%91%E6%9D%A1&type=1&token=NjAyshrrB0LcKeNTttJPBilVekuIDudP&act=0&id=221429&debug=1&pay_type=1&notify_url=&return_url=
        //http://localhost:8006/front/payment/codePay.html?price=0.01&type=1&pay_id=1&param=

        //判空
        if (price == null) {
            price = "1";
        }
        //参数有中文则需要URL编码
        String url = "http://codepay.fateqq.com:52888/creat_order?id=" + codePayId + "&pay_id=" + payId + "&price=" + price + "&type=" + type + "&token=" + token + "&param=" + param + "&notifyUrl=" + notifyUrl + "&returnUrl=" + returnUrl;

        //跳转
        response.sendRedirect(url);
    }

    @Override
    public void toRecharge(HttpServletResponse response, String payId) throws IOException {

        //拼接url
        //https://codepay.fateqq.com/codepay.html?id=221429&token=NjAyshrrB0LcKeNTttJPBilVekuIDudP&style=1&debug=1
        //参数有中文则需要URL编码
        String url = "https://codepay.fateqq.com/codepay.html?id=" + codePayId + "&pay_id=" + payId + "&token=" + token + "&style=1&debug=1";
        //跳转
        response.sendRedirect(url);
    }

    @Override
    public String toNotify(Map<String, String> params) throws NoSuchAlgorithmException {

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
            User user = userServiceImpl.mySelectUserByUserName(userName);
            if (ToolUtil.objIsEmpty(user)){
                //找充了多少钱
                Float price = Float.parseFloat(params.get("price"));
                user.setBalance(user.getBalance() + price);
                userServiceImpl.myUpdateById(user);
            }
            return "ok";
        } else {
            //参数不合法
            return "fail";
        }
    }
}
