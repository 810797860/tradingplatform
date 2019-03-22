package com.secondhand.tradingplatformgeccocontroller.scheduler;

import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.spider.SpiderThreadLocal;

/**
 * 被DeriveSchedulerContext替代，特指派生队列
 * 
 * @author huchengyi
 *
 */
@Deprecated
public class SchedulerContext {
	
	public static void into(HttpRequest request) {
		SpiderThreadLocal.get().getSpiderScheduler().into(request);
	}

}
