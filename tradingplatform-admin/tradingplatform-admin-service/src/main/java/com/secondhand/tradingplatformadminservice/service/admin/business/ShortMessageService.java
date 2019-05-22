package com.secondhand.tradingplatformadminservice.service.admin.business;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

/**
 * @author zhangjk
 * @description : 阿里云短信 服务实现类
 * ---------------------------------
 * @since 2018-12-14
 */
public interface ShortMessageService{

    /**
     * 测试发送邮件
     * @return
     * @throws ClientException
     */
    SendSmsResponse testSendEmail() throws ClientException;
}
