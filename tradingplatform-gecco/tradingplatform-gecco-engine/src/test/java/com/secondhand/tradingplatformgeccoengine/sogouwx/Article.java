package com.secondhand.tradingplatformgeccoengine.sogouwx;

import com.secondhand.tradingplatformgeccoengine.annotation.Gecco;
import com.secondhand.tradingplatformgeccoengine.annotation.HtmlField;
import com.secondhand.tradingplatformgeccoengine.annotation.Text;
import com.secondhand.tradingplatformgeccoengine.spider.HtmlBean;


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
