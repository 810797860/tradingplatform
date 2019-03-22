package com.secondhand.tradingplatformgeccocontroller.downloader;

import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;
import com.secondhand.tradingplatformgeccocontroller.response.HttpResponse;

public interface AfterDownload {
	
	public void process(HttpRequest request, HttpResponse response);

}
