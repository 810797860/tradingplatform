package com.secondhand.tradingplatformgeccoengine.listener;

import com.secondhand.tradingplatformgeccoengine.GeccoEngine;

/**
 * 简单的引擎时间兼容实现类，可以继承该类覆盖需要的方法
 * 
 * @author LiuJunGuang
 */
public abstract class SimpleEventListener implements EventListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secondhand.tradingplatformgeccoengine.listener.EventListener#onStart(com.secondhand.tradingplatformgeccoengine.GeccoEngine)
	 */
	@Override
	public void onStart(GeccoEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secondhand.tradingplatformgeccoengine.listener.EventListener#onPause(com.secondhand.tradingplatformgeccoengine.GeccoEngine)
	 */
	@Override
	public void onPause(GeccoEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secondhand.tradingplatformgeccoengine.listener.EventListener#onRestart(com.secondhand.tradingplatformgeccoengine.GeccoEngine)
	 */
	@Override
	public void onRestart(GeccoEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secondhand.tradingplatformgeccoengine.listener.EventListener#onStop(com.secondhand.tradingplatformgeccoengine.GeccoEngine)
	 */
	@Override
	public void onStop(GeccoEngine ge) {
	}

}
