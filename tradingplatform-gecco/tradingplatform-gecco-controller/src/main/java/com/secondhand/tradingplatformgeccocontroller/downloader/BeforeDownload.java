package com.secondhand.tradingplatformgeccocontroller.downloader;

import com.secondhand.tradingplatformgeccocontroller.request.HttpRequest;

public interface BeforeDownload {
	
	public void process(HttpRequest request);

}
