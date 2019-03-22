package com.secondhand.tradingplatformgeccoengine.jd;

import com.secondhand.tradingplatformgeccoengine.annotation.HtmlField;
import com.secondhand.tradingplatformgeccoengine.annotation.Text;
import com.secondhand.tradingplatformgeccoengine.spider.HrefBean;
import com.secondhand.tradingplatformgeccoengine.spider.HtmlBean;

import java.util.List;

public class Category implements HtmlBean {

	private static final long serialVersionUID = 3018760488621382659L;

	@Text
	@HtmlField(cssPath="dt a")
	private String parentName;
	
	@HtmlField(cssPath="dd a")
	private List<HrefBean> categorys;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<HrefBean> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<HrefBean> categorys) {
		this.categorys = categorys;
	}
	
}
