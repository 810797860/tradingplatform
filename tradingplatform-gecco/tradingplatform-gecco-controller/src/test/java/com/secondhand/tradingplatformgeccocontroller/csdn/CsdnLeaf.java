package com.secondhand.tradingplatformgeccocontroller.csdn;

import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.annotation.Text;
import com.secondhand.tradingplatformgeccocontroller.spider.HrefBean;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;

import java.util.List;

public class CsdnLeaf implements HtmlBean{

    private static final long serialVersionUID = -1L;

    @HtmlField(cssPath = "#content_views > p:nth-child(2) > font > b > a")
    private List<HrefBean> hrefBeanList;

    public List<HrefBean> getHrefBeanList() {
        return hrefBeanList;
    }

    public void setHrefBeanList(List<HrefBean> hrefBeanList) {
        this.hrefBeanList = hrefBeanList;
    }
}
