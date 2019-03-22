package com.secondhand.tradingplatformgeccocontroller.pipeline;

import com.alibaba.fastjson.JSON;
import com.secondhand.tradingplatformgeccocontroller.annotation.PipelineName;
import com.secondhand.tradingplatformgeccocontroller.spider.SpiderBean;

@PipelineName("consolePipeline")
public class ConsolePipeline implements Pipeline<SpiderBean> {

	@Override
	public void process(SpiderBean bean) {
		System.out.println(JSON.toJSONString(bean));
	}

}
