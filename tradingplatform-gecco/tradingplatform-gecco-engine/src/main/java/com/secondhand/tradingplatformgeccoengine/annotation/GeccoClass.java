package com.secondhand.tradingplatformgeccoengine.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeccoClass {

	Class<?>[] value();
}
