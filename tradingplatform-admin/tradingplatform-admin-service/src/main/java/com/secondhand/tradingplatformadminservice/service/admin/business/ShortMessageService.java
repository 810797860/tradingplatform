package com.secondhand.tradingplatformadminservice.service.admin.business;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangjk
 * @description : 阿里云短信 服务实现类
 * ---------------------------------
 * @since 2018-12-14
 */
public interface ShortMessageService{

    /**
     * 发送验证码
     * @param httpServletRequest
     * @param phoneNumbers
     * @return
     * @throws ClientException
     */
    SendSmsResponse sendVerificationCode(HttpServletRequest httpServletRequest, String phoneNumbers) throws ClientException;

    /**
     * 通知购买成功
     * @param phoneNumbers
     * @return
     * @throws ClientException
     */
    SendSmsResponse notifyPurchaseSuccess(String phoneNumbers) throws ClientException;
}
