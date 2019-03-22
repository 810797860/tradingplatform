package com.secondhand.tradingplatformgeccoengine.spider.render;

import com.secondhand.tradingplatformgeccoengine.request.HttpRequest;
import com.secondhand.tradingplatformgeccoengine.response.HttpResponse;
import com.secondhand.tradingplatformgeccoengine.spider.SpiderBean;
import net.sf.cglib.beans.BeanMap;

public interface FieldRender {
	
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean);

}
