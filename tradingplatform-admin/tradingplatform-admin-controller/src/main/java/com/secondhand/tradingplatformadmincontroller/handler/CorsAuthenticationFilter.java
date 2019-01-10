//package com.secondhand.tradingplatformadmincontroller.handler;
//
//import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
//import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 给shiro增加一个过滤器，过滤OPTIONS请求
// * @author 81079
// */
//public class CorsAuthenticationFilter extends FormAuthenticationFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(CorsAuthenticationFilter.class);
//
//    @Override
//    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//
//        if (((HttpServletRequest) request).getMethod().toUpperCase().equals(MagicalValue.STRING_OF_OPTIONS)) {
//            return true;
//        }
//        return super.isAccessAllowed(request, response, mappedValue);
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        logger.info("未登录");
//        return false;
//    }
//}
