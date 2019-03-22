package com.secondhand.tradingplatformgeccocontroller.downloader.proxy;

import com.secondhand.tradingplatformgeccocontroller.spider.Spider;
import com.secondhand.tradingplatformgeccocontroller.spider.SpiderThreadLocal;

public class ProxysContext {
	
	public static Proxys get() {
		Spider spider = SpiderThreadLocal.get();
		if(spider == null) {
			return null;
		}
		return spider.getEngine().getProxysLoader();
	}
	
	public static boolean isEnableProxy() {
		Spider spider = SpiderThreadLocal.get();
		if(spider == null) {
			return false;
		}
		return spider.getEngine().isProxy();
	}

}
