package com.qlbs.Bridge.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qlbs.Bridge.module.common.impl.PreOrderParam;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterMapping {

	Class<?> loginParam();

	Class<?> orderParam() default PreOrderParam.class;

	Class<?> exchargeParam();

}
