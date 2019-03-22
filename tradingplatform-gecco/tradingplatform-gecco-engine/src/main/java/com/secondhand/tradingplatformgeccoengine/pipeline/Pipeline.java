package com.secondhand.tradingplatformgeccoengine.pipeline;

import com.secondhand.tradingplatformgeccoengine.spider.SpiderBean;

public interface Pipeline<T extends SpiderBean> {

	public void process(T bean);

}
