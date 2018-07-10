package com.muti.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Desciption 数据源注解
 * Create By  li.bo
 * CreateTime 2018/7/10 11:26
 * UpdateTime 2018/7/10 11:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DS {
    String value() default "datasource1";
}
