package com.secondhand.tradingplatformgeccocontroller.spider.render;

import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.response.HttpResponse;
import com.secondhand.tradingplatformgeccocontroller.spider.SpiderBean;
import net.sf.cglib.beans.BeanMap;

public interface FieldRender {
	
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean);

}
