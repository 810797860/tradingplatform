package com.secondhand.tradingplatformgeccocontroller.spider.conversion;

public class IntegerTypeHandle implements TypeHandle<Integer> {
	
	@Override
	public Integer getValue(Object src) throws Exception {
		return Integer.valueOf(src.toString());
	}

}
