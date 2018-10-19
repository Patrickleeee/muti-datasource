package com.muti.common;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Desciption 扩展线程池（同步和异步获取）
 * Create By  li.bo
 * CreateTime 2018/9/25 16:16
 * UpdateTime 2018/9/25 16:16
 */
public class ExtendForkJoinPool extends ForkJoinPool {

    public ExtendForkJoinPool() {
    }

    public ExtendForkJoinPool(int parallelism) {
        super(parallelism);
    }

    public ExtendForkJoinPool(int parallelism, ForkJoinWorkerThreadFactory factory, Thread.UncaughtExceptionHandler handler, boolean asyncMode) {
        super(parallelism, factory, handler, asyncMode);
    }

    /**
     * 同步阻塞时，对每个task执行join，确保执行完毕
     * @param task
     * @param <T>
     * @return
     */
    public <T> T invoke(ForkJoinTask<T> task) {
        if (task instanceof AbstractTaskLoader) {
            super.invoke(task);
            return (T) ((AbstractTaskLoader) task).getContext();
        }
        return super.invoke(task);
    }
}
