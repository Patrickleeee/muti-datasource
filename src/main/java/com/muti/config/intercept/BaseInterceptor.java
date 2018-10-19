package com.muti.config.intercept;

import com.muti.bean.db.Dialect;
import org.apache.ibatis.plugin.Interceptor;

import java.io.Serializable;

/**
 * Desciption mybatis基础拦截器
 * Create By  li.bo
 * CreateTime 2018/10/18 11:05
 * UpdateTime 2018/10/18 11:05
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {

    private static final long serialVersionUID = 1L;

    protected Dialect DIALECT; //数据库方言

    /**
     * 设置注册拦截器时设定的属性
     */
//    protected void initProperties(Properties p) {
//        String dialectClass = p.getProperty("dialect");
//        if (StringUtils.isEmpty(dialectClass)) {
//            try {
//                throw new PropertyException("数据库分页方言无法找到!");
//            } catch (PropertyException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Dialect.Type dialectType = Dialect.Type.valueOf(dialectClass.toUpperCase());
//            Dialect dialect = null;
//            switch (dialectType) {
//                case ORACLE:
//                    dialect = new OracleSQLDialect();
//                    break;
//                // 需要实现MySQL的分页逻辑
//                case MYSQL:
//                    dialect = new MySQLDialect();
//                    break;
//            }
//            if (dialect == null) {
//                throw new NullPointerException("方言实例错误");
//            }
//            this.DIALECT = dialect;
//        }
//
//        if (StringUtils.isEmpty(p.getProperty("sqlPattern"))) {
//            try {
//                throw new PropertyException("sqlPattern property is not found!");
//            } catch (PropertyException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
