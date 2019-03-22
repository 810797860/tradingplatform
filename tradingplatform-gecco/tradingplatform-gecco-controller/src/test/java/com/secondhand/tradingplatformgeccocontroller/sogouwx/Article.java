package com.secondhand.tradingplatformgeccocontroller.sogouwx;

import com.secondhand.tradingplatformgeccocontroller.annotation.Gecco;
import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.annotation.Text;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;


@Gecco(matchUrl="http://mp.weixin.qq.com/s?{params}", pipelines="consolePipeline")
public class Article implements HtmlBean {

	private static final long serialVersionUID = 5310736056776105882L;

	@Text(own=false)
	@HtmlField(cssPath="body")
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
