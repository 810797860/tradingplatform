package com.secondhand.tradingplatformadmincontroller.config;

import com.secondhand.tradingplatformadmincontroller.schedule.MultiThreadScheduleTask;
import com.secondhand.tradingplatformcommon.util.ApplicationContextHolder;
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
}
