package com.secondhand.tradingplatformgeccocontroller.downloader;

import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.response.HttpResponse;

/**
 * 下载器，负责将Scheduler里的请求下载下来，系统默认采用HttpClient作为下载引擎。
 * 
 * @author huchengyi
 *
 */
public interface Downloader {
	
	public HttpResponse download(HttpRequest request, int timeout) throws DownloadException;
	
	public void shutdown();
}
