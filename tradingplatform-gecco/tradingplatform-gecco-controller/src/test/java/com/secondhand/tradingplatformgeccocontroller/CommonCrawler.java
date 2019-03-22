package com.secondhand.tradingplatformgeccocontroller;

import com.secondhand.tradingplatformgeccocontroller.annotation.Gecco;
import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.annotation.Request;
import com.secondhand.tradingplatformgeccocontroller.annotation.Text;
import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;

@Gecco(pipelines="consolePipeline")
public class CommonCrawler implements HtmlBean {

	private static final long serialVersionUID = -8870768223740844229L;

	@Request
	private HttpRequest request;
	
	@Text(own=false)
	@HtmlField(cssPath="body")
	private String body;

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		GeccoEngine.create()
		.classpath("com.secondhand.tradingplatformgeccocontroller.demo")
		.start("https://www.baidu.com/")
		.interval(2000)
		.start();
	}
}
