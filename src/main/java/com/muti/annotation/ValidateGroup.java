package com.muti.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Desciption 非空校验组
 * Create By  li.bo
 * CreateTime 2018/7/17 10:33
 * UpdateTime 2018/7/17 10:33
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateGroup {

    public ValidateField[] fields();
}
