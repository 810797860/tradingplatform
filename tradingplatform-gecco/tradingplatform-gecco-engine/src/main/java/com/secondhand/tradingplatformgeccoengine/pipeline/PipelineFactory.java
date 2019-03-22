package com.secondhand.tradingplatformgeccoengine.pipeline;

import com.secondhand.tradingplatformgeccoengine.spider.SpiderBean;

public interface PipelineFactory {
	
	public Pipeline<? extends SpiderBean> getPipeline(String name);

}
