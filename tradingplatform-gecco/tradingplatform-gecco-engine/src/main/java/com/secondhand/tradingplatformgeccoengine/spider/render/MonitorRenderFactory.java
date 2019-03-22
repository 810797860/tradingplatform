package com.secondhand.tradingplatformgeccoengine.spider.render;

import com.secondhand.tradingplatformgeccoengine.monitor.RenderMointorIntercetor;
import com.secondhand.tradingplatformgeccoengine.spider.render.html.HtmlRender;
import com.secondhand.tradingplatformgeccoengine.spider.render.json.JsonRender;
import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;

public class MonitorRenderFactory extends RenderFactory {

	public MonitorRenderFactory(Reflections reflections) {
		super(reflections);
	}

	@Override
	public HtmlRender createHtmlRender() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HtmlRender.class);
		enhancer.setCallback(new RenderMointorIntercetor());
		return (HtmlRender)enhancer.create();
	}

	@Override
	public JsonRender createJsonRender() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(JsonRender.class);
		enhancer.setCallback(new RenderMointorIntercetor());
		return (JsonRender)enhancer.create();
	}
	
	
	
}
