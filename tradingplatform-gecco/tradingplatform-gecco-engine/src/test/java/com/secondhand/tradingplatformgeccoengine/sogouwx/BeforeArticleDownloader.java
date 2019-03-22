package com.secondhand.tradingplatformgeccoengine.sogouwx;

import com.secondhand.tradingplatformgeccoengine.annotation.GeccoClass;
import com.secondhand.tradingplatformgeccoengine.downloader.BeforeDownload;
import com.secondhand.tradingplatformgeccoengine.request.HttpRequest;

@GeccoClass(Article.class)
public class BeforeArticleDownloader implements BeforeDownload {

	@Override
	public void process(HttpRequest request) {
		request.clearCookie();
		request.clearHeader();
	}

}
