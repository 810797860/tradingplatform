package com.secondhand.tradingplatformadmincontroller.log.factory;

import com.secondhand.tradingplatformadminentity.entity.admin.business.LoginLog;
import com.secondhand.tradingplatformadminentity.entity.admin.business.OperationLog;
import com.secondhand.tradingplatformcommon.state.LogSucceed;
import com.secondhand.tradingplatformcommon.state.LogType;

import java.util.Date;

/**
 * 日志对象创建工厂
 *
 * @author 81079
 */

public class LogFactory {

    /**
     * 创建操作日志
     */
    public static OperationLog createOperationLog(LogType logType, Long userId, String bussinessName, String clazzName, String methodName, String msg, LogSucceed succeed) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogtype(logType.getMessage());
        operationLog.setLogname(bussinessName);
        operationLog.setUserid(userId);
        operationLog.setClassname(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreatetime(new Date());
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setMessage(msg);
        return operationLog;
    }

    /**
     * 创建登录日志
     */
    public static LoginLog createLoginLog(LogType logType, Long userId, String msg, String ip) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLogname(logType.getMessage());
        loginLog.setUserid(userId);
        loginLog.setCreatetime(new Date());
        loginLog.setSucceed(LogSucceed.SUCCESS.getMessage());
        loginLog.setIp(ip);
        loginLog.setMessage(msg);
        return loginLog;
    }
}
