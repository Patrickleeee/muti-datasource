package com.muti.common;

import java.util.concurrent.RecursiveAction;

/**
 * Desciption 基本task的抽象实现类
 * Create By  li.bo
 * CreateTime 2018/9/25 14:55
 * UpdateTime 2018/9/25 14:55
 */
public abstract class AbstractTaskLoader<T> extends RecursiveAction implements TaskLoader {

    /**
     * 返回结果
     */
    protected T context;

    public AbstractTaskLoader(T context) {
        this.context = context;
    }

    public void compute() {
        load(context);
    }

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }
}
