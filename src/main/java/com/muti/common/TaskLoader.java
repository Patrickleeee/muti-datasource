package com.muti.common;

/**
 * Desciption 基本task接口
 * Create By  li.bo
 * CreateTime 2018/9/25 14:52
 * UpdateTime 2018/9/25 14:52
 */
public interface TaskLoader<T> {

    void load(T context);
}
