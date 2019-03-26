package com.secondhand.tradingplatformgeccocontroller.csdn;

import com.secondhand.tradingplatformgeccocontroller.GeccoEngine;
import com.secondhand.tradingplatformgeccocontroller.annotation.Gecco;
import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.request.HttpGetRequest;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;

import java.util.List;

@Gecco(matchUrl = "https://blog.csdn.net/weixin_41835916/article/details/81413498", pipelines = "consolePipeline")
public class CsdnHref implements HtmlBean{

    private static final long serialVersionUID = -1L;

    //标题
    @HtmlField(cssPath = "#content_views")
    private List<CsdnLeaf> csdnLeaves;

    public List<CsdnLeaf> getCsdnLeaves() {
        return csdnLeaves;
    }

    public void setCsdnLeaves(List<CsdnLeaf> csdnLeaves) {
        this.csdnLeaves = csdnLeaves;
    }

    public static void main(String[] args){
        HttpGetRequest httpGetRequest = new HttpGetRequest("https://blog.csdn.net/weixin_41835916/article/details/81413498");
        httpGetRequest.setCharset("GBK");
        GeccoEngine.create()
                .classpath("com.secondhand.tradingplatformgeccocontroller.csdn")
                .start(httpGetRequest)
                .thread(1)
                .interval(2000)
                .run();
    }
}
