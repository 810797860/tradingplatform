package com.secondhand.tradingplatformgeccoengine.downloader;

import com.secondhand.tradingplatformgeccoengine.request.HttpRequest;
import com.secondhand.tradingplatformgeccoengine.response.HttpResponse;
import com.secondhand.tradingplatformgeccoengine.spider.SpiderBeanContext;
import com.secondhand.tradingplatformgeccoengine.spider.SpiderThreadLocal;

/**
 * 获得当前线程，正在抓取的SpiderBean的下载器
 * 
 * @author huchengyi
 *
 */
public class DownloaderContext {
	
	public static HttpResponse download(HttpRequest request) throws DownloadException {
		SpiderBeanContext context = SpiderThreadLocal.get().getSpiderBeanContext();
		return context.getDownloader().download(request, context.getTimeout());
	}
	
	public static HttpResponse defaultDownload(HttpRequest request) throws DownloadException {
		SpiderBeanContext context = SpiderThreadLocal.get().getSpiderBeanContext();
		Downloader downloader = SpiderThreadLocal.get().getEngine().getSpiderBeanFactory().getDownloaderFactory().defaultDownloader();
		return downloader.download(request, context.getTimeout());
	}
	

}
