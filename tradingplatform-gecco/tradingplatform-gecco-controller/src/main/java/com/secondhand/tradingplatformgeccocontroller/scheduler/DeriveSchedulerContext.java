package com.secondhand.tradingplatformgeccocontroller.scheduler;

import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.spider.SpiderThreadLocal;

/**
 * 派生队列上下文，可以在运行时将请求放入派生队列
 * 
 * @author huchengyi
 *
 */
public class DeriveSchedulerContext {
	
	public static void into(HttpRequest request) {
		SpiderThreadLocal.get().getSpiderScheduler().into(request);
	}

}
