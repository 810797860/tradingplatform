package com.secondhand.tradingplatformadmincontroller.config;

import com.secondhand.tradingplatformcommon.util.ApplicationContextHolder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用于注入子模块的bean
 * @author 81079
 */

@Configuration
public class WebConfig {

    @Bean
    public ApplicationContextHolder applicationContextHolder(){
        return new ApplicationContextHolder();
    }

//    /**
//     * 解决跨域问题
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean globalFilterBean(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new ShiroOriginFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }
}
