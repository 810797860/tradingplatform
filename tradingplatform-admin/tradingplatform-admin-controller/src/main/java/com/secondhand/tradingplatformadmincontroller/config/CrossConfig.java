/*
package com.secondhand.tradingplatformadmincontroller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry){

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedHeaders("Content-Type,Content-Length,Authorization,Accept,X-Requested-With")
                .allowedMethods("PUT,POST,GET,DELETE,OPTIONS,HEAD")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
*/
