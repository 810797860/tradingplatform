package com.secondhand.tradingplatformgeccoengine.scheduler;

import com.secondhand.tradingplatformgeccoengine.request.HttpRequest;
import com.secondhand.tradingplatformgeccoengine.spider.SpiderThreadLocal;

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
