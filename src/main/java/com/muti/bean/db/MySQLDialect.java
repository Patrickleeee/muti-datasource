package com.muti.bean.db;


import com.muti.bean.dto.PageDto;

/**
 * Desciption Mysql方言实现
 * Create By  li.bo
 * CreateTime 2018/10/17 17:11
 * UpdateTime 2018/10/17 17:11
 */
public class MySQLDialect implements Dialect {

    /**
     * 获取Mysql数据库的分页查询语句
     *
     * @param page 分页对象
     * @param sql  包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    @Override
    public String getLimitString(PageDto page, String sql) {
        // 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        // System.out.println("page:"+page.getPage()+"-------"+page.getRows());
        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append(sql);
        pagingSelect.append(" limit ").append(page.getOffset()).append(",").append(page.getRows());
        return pagingSelect.toString();
    }
}
