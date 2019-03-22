package com.secondhand.tradingplatformgeccocontroller.spider.render;

import com.secondhand.tradingplatformgeccocontroller.spider.SpiderThreadLocal;

public class RenderContext {
	
	public static Render getRender(RenderType type){
		return SpiderThreadLocal.get().getEngine().getSpiderBeanFactory().getRenderFactory().getRender(type);
	}

}
