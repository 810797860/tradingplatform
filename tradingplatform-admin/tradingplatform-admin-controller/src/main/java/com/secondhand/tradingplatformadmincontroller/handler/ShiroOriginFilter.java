package com.secondhand.tradingplatformadmincontroller.handler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 全局拦截器
 * 为了设置response头解决跨域问题
 *
 * @author 81079
 */
public class ShiroOriginFilter implements Filter {

/*    // 多个跨域域名设置
    public static final String[] ALLOW_DOMAIN = {"http://localhost:8080"};*/

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        // 设置允许多个域名请求
        String[] allowDomains = {"http://www.oooojbk.com:8024", "http://localhost:8024"};
        Set allowOrigins = new HashSet(Arrays.asList(allowDomains));
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String originHeads = req.getHeader("Origin");
        if(allowOrigins.contains(originHeads)){
            //设置允许跨域的配置
            // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
            httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeads);
        }
//        httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://www.oooojbk.com:8024");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length,Authorization,Accept,X-Requested-With");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS,HEAD");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("X-Powered-By", "3.2.1");
        //sql,xss过滤
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
/*        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(
                httpServletRequest);*/
        filterChain.doFilter(httpServletRequest, servletResponse);

/*        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=utf-8");
        //跨域设置
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String originHeader = req.getHeader("Origin");
        if (Arrays.asList(ALLOW_DOMAIN).contains(originHeader)) {
            //通过在响应 header 中设置 ‘*’ 来允许来自所有域的跨域请求访问。
            res.setHeader("Access-Control-Allow-Origin", originHeader);
            //通过对 Credentials 参数的设置，就可以保持跨域 Ajax 时的 Cookie
            //设置了Allow-Credentials，Allow-Origin就不能为*,需要指明具体的url域
            res.setHeader("Access-Control-Allow-Credentials", "true");
            //请求方式
            res.setHeader("Access-Control-Allow-Methods", "*");
            //（预检请求）的返回结果（即 Access-Control-Allow-Methods 和Access-Control-Allow-Headers 提供的信息） 可以被缓存多久
            res.setHeader("Access-Control-Max-Age", "86400");
            //首部字段用于预检请求的响应。其指明了实际请求中允许携带的首部字段
            //res.setHeader("Access-Control-Allow-Headers", "*");
            res.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length,Authorization,Accept,X-Requested-With");
//            res.setHeader("Access-Control-Allow-Headers", "Timestamp,Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");
        }
        //sql,xss过滤
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(
                httpServletRequest);
        filterChain.doFilter(xssHttpServletRequestWrapper, servletResponse);*/
    }

    @Override
    public void destroy() {

    }
}