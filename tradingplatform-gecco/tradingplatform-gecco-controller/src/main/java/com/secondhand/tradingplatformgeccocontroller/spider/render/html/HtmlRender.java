package com.secondhand.tradingplatformgeccocontroller.spider.render.html;

import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.response.HttpResponse;
import com.secondhand.tradingplatformgeccocontroller.spider.SpiderBean;
import com.secondhand.tradingplatformgeccocontroller.spider.render.AbstractRender;
import net.sf.cglib.beans.BeanMap;

/**
 * 将下载下来的html映射到bean中
 * 
 * @author huchengyi
 *
 */
public class HtmlRender extends AbstractRender {
	
	private HtmlFieldRender htmlFieldRender;
	
	private AjaxFieldRender ajaxFieldRender;
	
	private JSVarFieldRender jsVarFieldRender;
	
	private ImageFieldRender imageFieldRender;
	
	public HtmlRender() {
		super();
		this.htmlFieldRender = new HtmlFieldRender();
		this.ajaxFieldRender = new AjaxFieldRender();
		this.jsVarFieldRender = new JSVarFieldRender();
		this.imageFieldRender = new ImageFieldRender();
	}

	@Override
	public void fieldRender(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		htmlFieldRender.render(request, response, beanMap, bean);
		ajaxFieldRender.render(request, response, beanMap, bean);
		jsVarFieldRender.render(request, response, beanMap, bean);
		imageFieldRender.render(request, response, beanMap, bean);
	}

}
