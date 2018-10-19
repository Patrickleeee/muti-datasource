package com.muti.common;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/9/25 15:06
 * UpdateTime 2018/9/25 15:06
 */
public class DefaultForkJoinTaskLoader<T> extends AbstractTaskLoader{

    /**
     * 待执行的任务列表
     */
    private List<AbstractTaskLoader> taskList;

    /**
     * 构造器
     * @param context
     */
    public DefaultForkJoinTaskLoader(T context) {
        super(context);
        this.taskList = new ArrayList<>();
    }

    public DefaultForkJoinTaskLoader<T> addTask(TaskLoader taskLoader) {
        taskList.add(new AbstractTaskLoader(this.context) {
            @Override
            public void load(Object context) {
                taskLoader.load(context);
            }
        });
        return this;
    }

    /**
     * fork拆分任务
     * @param context
     */
    @Override
    public void load(Object context) {
        this.taskList.forEach(ForkJoinTask::fork);
    }

    /**
     * 获取执行后的结果
     * @return
     */
    public Object getContext() {
        this.taskList.forEach(ForkJoinTask::join);
        return this.context;
    }
}
