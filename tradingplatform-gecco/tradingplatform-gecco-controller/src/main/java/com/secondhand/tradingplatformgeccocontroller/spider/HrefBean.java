package com.secondhand.tradingplatformgeccocontroller.spider;

import com.secondhand.tradingplatformgeccocontroller.annotation.Href;
import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.annotation.Text;

public class HrefBean implements SpiderBean {

	private static final long serialVersionUID = -3770871271092767593L;

	@Href
	@HtmlField(cssPath="a")
	private String url;
	
	@Text
	@HtmlField(cssPath="a")
	private String title;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
