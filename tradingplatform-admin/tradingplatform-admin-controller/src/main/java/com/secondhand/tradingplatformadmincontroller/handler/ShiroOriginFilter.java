//package com.secondhand.tradingplatformadmincontroller.handler;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 全局拦截器
// * 为了设置response头解决跨域问题
// * @author 81079
// */
//public class ShiroOriginFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}