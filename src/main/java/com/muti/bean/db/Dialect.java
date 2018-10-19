package com.muti.bean.db;

import com.muti.bean.dto.PageDto;

/**
 * Desciption 数据库方言
 * Create By  li.bo
 * CreateTime 2018/10/17 17:11
 * UpdateTime 2018/10/17 17:11
 */
public interface Dialect {

    /**
     * 获取数据库的分页查询语句
     *
     * @param page 分页对象
     * @param sql  包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    public String getLimitString(PageDto page, String sql);

    /**
     * 数据库支持类型
     */
    public static enum Type {
        MYSQL, ORACLE
    }
}
