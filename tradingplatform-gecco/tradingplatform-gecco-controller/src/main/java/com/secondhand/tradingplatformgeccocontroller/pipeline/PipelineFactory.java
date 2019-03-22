package com.secondhand.tradingplatformgeccocontroller.pipeline;

import com.secondhand.tradingplatformgeccocontroller.spider.SpiderBean;

public interface PipelineFactory {
	
	public Pipeline<? extends SpiderBean> getPipeline(String name);

}
