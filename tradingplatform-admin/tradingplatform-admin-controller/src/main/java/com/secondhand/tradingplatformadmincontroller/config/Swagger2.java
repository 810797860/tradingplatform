package com.secondhand.tradingplatformadmincontroller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 81079
 */

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.secondhand.tradingplatformadmincontroller.controller"})
public class Swagger2 {

    /**
     * 生成SWAGGER2的api
     *
     * @return
     */
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("zhangjk", "http://www.baidu.com", "810797860@qq.com");
        return new ApiInfoBuilder()
                .title("mybatis-plus-demo")
                .description("自动生成mybatis-plus相应文件")
                .contact(contact)
                .version("1.1.0")
                .build();
    }
}
