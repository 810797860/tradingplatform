package com.secondhand.tradingplatformadmincontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @EnableCaching(开启Redis二级缓存)
 * @author 81079
 */

@SpringBootApplication
public class TradingplatformAdminControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingplatformAdminControllerApplication.class, args);
	}
}
