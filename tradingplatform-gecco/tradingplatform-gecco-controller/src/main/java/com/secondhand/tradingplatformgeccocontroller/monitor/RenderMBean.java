package com.secondhand.tradingplatformgeccocontroller.monitor;

import com.alibaba.fastjson.JSON;
import com.secondhand.tradingplatformgeccocontroller.annotation.MBean;
import org.weakref.jmx.Managed;

@MBean("render")
public class RenderMBean {
	
	private String statistics;

	@Managed
	public String getStatistics() {
		return statistics;
	}

	@Managed
	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
	
	@Managed
	public void refresh() {
		setStatistics(JSON.toJSONString(RenderMonitor.getStatistics()));
	}
}
