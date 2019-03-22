package com.secondhand.tradingplatformgeccocontroller.listener;

import com.secondhand.tradingplatformgeccocontroller.GeccoEngine;

/**
 * 简单的引擎时间兼容实现类，可以继承该类覆盖需要的方法
 * 
 * @author LiuJunGuang
 */
public abstract class SimpleEventListener implements EventListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secondhand.tradingplatformgeccocontroller.listener.EventListener#onStart(com.secondhand.tradingplatformgeccocontroller.GeccoEngine)
	 */
	@Override
	public void onStart(GeccoEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secondhand.tradingplatformgeccocontroller.listener.EventListener#onPause(com.secondhand.tradingplatformgeccocontroller.GeccoEngine)
	 */
	@Override
	public void onPause(GeccoEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secondhand.tradingplatformgeccocontroller.listener.EventListener#onRestart(com.secondhand.tradingplatformgeccocontroller.GeccoEngine)
	 */
	@Override
	public void onRestart(GeccoEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secondhand.tradingplatformgeccocontroller.listener.EventListener#onStop(com.secondhand.tradingplatformgeccocontroller.GeccoEngine)
	 */
	@Override
	public void onStop(GeccoEngine ge) {
	}

}
