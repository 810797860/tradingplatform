package com.secondhand.tradingplatformadmincontroller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @EnableCaching(开启Redis二级缓存)
 * @author 81079
 */

@SpringBootApplication
@EnableCaching
public class TradingplatformAdminControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingplatformAdminControllerApplication.class, args);
	}
}
