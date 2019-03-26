package com.secondhand.tradingplatformgeccocontroller.sportsSpecial;

import com.secondhand.tradingplatformgeccocontroller.annotation.PipelineName;
import com.secondhand.tradingplatformgeccocontroller.pipeline.Pipeline;
import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.spider.HrefBean;

import java.util.ArrayList;
import java.util.List;

@PipelineName("sportsSpecialListPipeline")
public class DangListPipeline implements Pipeline<DangPage> {

    public static List<HttpRequest> httpRequests = new ArrayList<>();

    @Override
    public void process(DangPage dangPage) {
        List<DangList> dangList = dangPage.getDangLists();
        if (dangList == null){
            return;
        }
        for (DangList list : dangList){
            List<HrefBean> hrefBeans = list.getHrefBeanList();
            for (HrefBean hrefBean : hrefBeans){
                String url = hrefBean.getUrl();
                if (url.indexOf("http://product.dangdang.com") == -1){
                    continue;
                }
                HttpRequest httpRequest = dangPage.getRequest();
                httpRequests.add(httpRequest.subRequest(url));
            }
        }
    }
}
