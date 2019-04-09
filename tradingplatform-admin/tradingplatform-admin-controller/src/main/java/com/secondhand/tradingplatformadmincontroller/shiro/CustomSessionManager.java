/*
package com.secondhand.tradingplatformadmincontroller.shiro;

import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

*/
/**
 * @author 81079
 *//*

@Configuration
public class CustomSessionManager extends DefaultWebSessionManager {
    */
/**
     * 获取请求头中key为“Authorization”的value == sessionId
     *//*

    private static final String AUTHORIZATION ="Authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "cookie";

    */
/**
     *  @Description shiro框架 自定义session获取方式<br/>
     *  可自定义session获取规则。这里采用ajax请求头 {@link AUTHORIZATION}携带sessionId的方式
     *//*

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // TODO Auto-generated method stub
        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (!ToolUtil.strIsEmpty(sessionId)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
        return super.getSessionId(request, response);
    }
}
*/
