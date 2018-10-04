package com.secondhand.tradingplatformadmincontroller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 81079
 */

@SpringBootApplication
public class TradingplatformAdminControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingplatformAdminControllerApplication.class, args);
	}
}
