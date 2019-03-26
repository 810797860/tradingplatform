package com.secondhand.tradingplatformgeccocontroller.sportsSpecial;

import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.annotation.JSONPath;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;
import com.secondhand.tradingplatformgeccocontroller.spider.JsonBean;

public class DangAjaxDetail implements HtmlBean{

    private static final long serialVersionUID = -1L;

    @HtmlField(cssPath = "body > div.descrip")
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
