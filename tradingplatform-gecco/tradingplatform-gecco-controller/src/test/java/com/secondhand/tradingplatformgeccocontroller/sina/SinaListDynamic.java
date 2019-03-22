package com.secondhand.tradingplatformgeccocontroller.sina;

import com.alibaba.fastjson.JSONObject;
import com.secondhand.tradingplatformgeccocontroller.GeccoEngine;
import com.secondhand.tradingplatformgeccocontroller.annotation.PipelineName;
import com.secondhand.tradingplatformgeccocontroller.dynamic.DynamicGecco;
import com.secondhand.tradingplatformgeccocontroller.pipeline.JsonPipeline;

@PipelineName("sinaListDynamicPipeline")
public class SinaListDynamic extends JsonPipeline {
	
	@Override
	public void process(JSONObject jo) {
		System.out.println(jo);		
	}
	
	public static void main(String[] args) {
		
		Class<?> item = DynamicGecco.html()
				.stringField("url").csspath("a").build()
				.stringField("tag").csspath("a").text().build()
				.register();
		
		DynamicGecco.html()
		.gecco("http://news.sina.com.cn/china/", "sinaListDynamicPipeline")
		.listField("items", item).csspath("#subShowContent1_static .news-item h2 a").build()
		.register();
		
		GeccoEngine.create()
		.classpath("com.secondhand.tradingplatformgeccocontroller.demo.sina")
		.start("http://news.sina.com.cn/china/")
		.run();
	}

}