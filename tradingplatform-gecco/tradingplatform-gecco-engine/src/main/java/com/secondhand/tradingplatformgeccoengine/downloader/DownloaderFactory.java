package com.secondhand.tradingplatformgeccoengine.downloader;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 下载器工厂类
 * 
 * @author huchengyi
 *
 */
public abstract class DownloaderFactory {
	
	public static final String DEFAULT_DWONLODER = "httpClientDownloader";
	
	private Map<String, Downloader> downloaders;
	
	public DownloaderFactory(Reflections reflections) {
		this.downloaders = new HashMap<String, Downloader>();
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(com.secondhand.tradingplatformgeccoengine.annotation.Downloader.class);
		for(Class<?> downloaderClass : classes) {
			com.secondhand.tradingplatformgeccoengine.annotation.Downloader downloader = (com.secondhand.tradingplatformgeccoengine.annotation.Downloader)downloaderClass.getAnnotation(com.secondhand.tradingplatformgeccoengine.annotation.Downloader.class);
			try {
				Object o = createDownloader(downloaderClass);
				if(o instanceof Downloader) {
					Downloader downloaderInstance = (Downloader)o;
					String name = downloader.value();
					downloaders.put(name, downloaderInstance);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public Downloader getDownloader(String name) {
		Downloader downloader = downloaders.get(name);
		if(downloader == null) {
			return defaultDownloader();
		}
		return downloader;
	}
	
	public Downloader defaultDownloader() {
		return downloaders.get(DEFAULT_DWONLODER);
	}

	protected abstract Object createDownloader(Class<?> downloaderClass) throws Exception;
	
	public void closeAll() {
		for(Downloader downloader : downloaders.values()) {
			downloader.shutdown();
		}
	}
}
