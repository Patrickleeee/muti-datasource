package com.muti.common;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/9/25 16:20
 * UpdateTime 2018/9/25 16:20
 */
public class ForkJoinPoolFactory {

    private int parallelism;

    private ExtendForkJoinPool forkJoinPool;

    public ForkJoinPoolFactory() {
        this(Runtime.getRuntime().availableProcessors() * 16);
    }

    public ForkJoinPoolFactory(int parallelism) {
        this.parallelism = parallelism;
        this.forkJoinPool = new ExtendForkJoinPool(parallelism);
    }

    public ExtendForkJoinPool getObject() {
        return this.forkJoinPool;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public void destory() throws Exception {
        this.forkJoinPool.shutdown();
    }
}
