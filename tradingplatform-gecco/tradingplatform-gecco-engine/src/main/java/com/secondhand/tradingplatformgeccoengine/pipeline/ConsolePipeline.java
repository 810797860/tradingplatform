package com.secondhand.tradingplatformgeccoengine.pipeline;

import com.alibaba.fastjson.JSON;
import com.secondhand.tradingplatformgeccoengine.annotation.PipelineName;
import com.secondhand.tradingplatformgeccoengine.spider.SpiderBean;

@PipelineName("consolePipeline")
public class ConsolePipeline implements Pipeline<SpiderBean> {

	@Override
	public void process(SpiderBean bean) {
		System.out.println(JSON.toJSONString(bean));
	}

}
