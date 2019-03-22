package com.secondhand.tradingplatformgeccoengine.spider.conversion;

public class BooleanTypeHandle implements TypeHandle<Boolean> {

	@Override
	public Boolean getValue(Object src) throws Exception {
		return Boolean.valueOf(src.toString().toLowerCase());
	}

}
