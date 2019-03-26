package com.secondhand.tradingplatformgeccocontroller.electricAppliance;

import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.spider.HrefBean;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;

import java.util.List;

public class DangList implements HtmlBean{

    private static final long serialVersionUID = -1L;

    @HtmlField(cssPath = "li p a")
    private List<HrefBean> hrefBeanList;

    public List<HrefBean> getHrefBeanList() {
        return hrefBeanList;
    }

    public void setHrefBeanList(List<HrefBean> hrefBeanList) {
        this.hrefBeanList = hrefBeanList;
    }
}
