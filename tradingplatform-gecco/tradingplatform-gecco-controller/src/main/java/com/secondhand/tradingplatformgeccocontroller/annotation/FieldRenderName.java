package com.secondhand.tradingplatformgeccocontroller.annotation;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldRenderName {

	String value();
	
}
