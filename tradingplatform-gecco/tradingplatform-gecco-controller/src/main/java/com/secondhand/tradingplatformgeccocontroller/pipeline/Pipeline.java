package com.secondhand.tradingplatformgeccocontroller.pipeline;

import com.secondhand.tradingplatformgeccocontroller.spider.SpiderBean;

public interface Pipeline<T extends SpiderBean> {

	public void process(T bean);

}
