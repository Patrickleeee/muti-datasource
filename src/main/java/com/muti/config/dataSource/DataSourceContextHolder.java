package com.muti.config.dataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Desciption 数据源切换
 * Create By  li.bo
 * CreateTime 2018/7/10 11:16
 * UpdateTime 2018/7/10 11:16
 */
@Slf4j
public class DataSourceContextHolder {

    public static final String DEFAULT_DB = "datasource1";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDb(String dbType) {
        log.info("切换到{"+dbType+"}数据源");
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return contextHolder.get();
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
