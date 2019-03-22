package com.secondhand.tradingplatformgeccoengine.downloader;

import com.secondhand.tradingplatformgeccoengine.request.HttpRequest;

public interface BeforeDownload {
	
	public void process(HttpRequest request);

}
