package com.secondhand.tradingplatformgeccocontroller.spider.conversion;

public class StringTypeHandle implements TypeHandle<String> {

	@Override
	public String getValue(Object src) {
		return src.toString();
	}

}
