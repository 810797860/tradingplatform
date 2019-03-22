package com.secondhand.tradingplatformgeccoengine.spider.render;

import com.secondhand.tradingplatformgeccoengine.spider.SpiderThreadLocal;

public class RenderContext {
	
	public static Render getRender(RenderType type){
		return SpiderThreadLocal.get().getEngine().getSpiderBeanFactory().getRenderFactory().getRender(type);
	}

}
