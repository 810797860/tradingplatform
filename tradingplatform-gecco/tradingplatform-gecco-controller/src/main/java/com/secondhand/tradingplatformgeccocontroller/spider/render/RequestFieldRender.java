package com.secondhand.tradingplatformgeccocontroller.spider.render;

import com.secondhand.tradingplatformgeccocontroller.annotation.Request;
import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.response.HttpResponse;
import com.secondhand.tradingplatformgeccocontroller.spider.SpiderBean;
import net.sf.cglib.beans.BeanMap;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class RequestFieldRender implements FieldRender {

	@Override
	@SuppressWarnings({"unchecked" })
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		Set<Field> requestFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(Request.class));
		for(Field field : requestFields) {
			beanMap.put(field.getName(), request);
		}
	}
	
}
