package com.secondhand.tradingplatformgeccocontroller.bookLibrary;

import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.annotation.JSONPath;
import com.secondhand.tradingplatformgeccocontroller.spider.JsonBean;

public class DangAjaxDetail implements JsonBean{

    private static final long serialVersionUID = -1L;

    @JSONPath("$.data.html")
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
