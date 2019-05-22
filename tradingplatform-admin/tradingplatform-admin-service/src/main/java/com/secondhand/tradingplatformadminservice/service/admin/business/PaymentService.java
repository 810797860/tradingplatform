package com.secondhand.tradingplatformadminservice.service.admin.business;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author zhangjk
 * @description : 码支付 服务实现类
 * ---------------------------------
 * @since 2018-12-14
 */
public interface PaymentService {

    /**
     * 接收参数，创建订单
     * @param response
     * @param price
     * @param type
     * @param payId
     * @param param
     * @throws IOException
     */
    void toCodePay(HttpServletResponse response, String price, String type, String payId, String param) throws IOException;

    /**
     * 充值页面
     * @param response
     * @param payId
     * @throws IOException
     */
    void toRecharge(HttpServletResponse response, String payId) throws IOException;

    /**
     * 验证通知，处理自己的业务
     * @param params
     * @return
     * @throws NoSuchAlgorithmException
     */
    String toNotify(Map<String, String> params) throws NoSuchAlgorithmException;
}
