package com.muti.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Desciption 非空校验
 * Create By  li.bo
 * CreateTime 2018/7/17 10:15
 * UpdateTime 2018/7/17 10:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateField {

    /**
     * 参数索引位置
     * @return
     */
    public int index() default -1;

    /**
     * 参数是基本数据类或String，不用指定该参数；
     * 参数是对象，需要验证对象里面的某个属性，就用改参数指定属性名
     * @return
     */
    public String fieldName() default "";

    /**
     * 正则校验
     * @return
     */
    public String regStr() default "";

    /**
     * 校验是否为空（true表示不能为空，false表示能为空 ）
     * @return
     */
    public boolean notNull() default false;

    /**
     * 最大长度校验
     * @return
     */
    public int maxLength() default -1;

    /**
     * 最小长度校验
     * @return
     */
    public int minLength() default -1;

    /**
     * 最大值校验
     * @return
     */
    public int maxValue() default -1;

    /**
     * 最小值校验
     * @return
     */
    public int minValue() default -1;
}
