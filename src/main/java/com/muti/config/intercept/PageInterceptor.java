package com.muti.config.intercept;

import com.muti.bean.db.MySQLDialect;
import com.muti.bean.dto.PageDto;
import com.muti.util.ReflectUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Desciption 分页拦截器（用于拦截需要进行分页查询的操作，然后对其进行分页处理）
 * Create By  li.bo
 * CreateTime 2018/10/17 17:11
 * UpdateTime 2018/10/17 17:11
 */
@Slf4j
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor extends BaseInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(statementHandler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();
            Object obj = boundSql.getParameterObject();
            if (obj instanceof PageDto) {
                PageDto page = (PageDto) obj;
                //获取delegate父类BaseStatementHandler的mappedStatement属性
                MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
                //拦截到的prepare方法参数是一个Connection对象
                Connection connection = (Connection) invocation.getArgs()[0];
                //获取当前要执行的Sql语句
                String sql = boundSql.getSql();
                //给当前的page参数对象设置总记录数
                this.setTotalRecord(page, mappedStatement, connection);
                //给当前的page参数对象补全完整信息
                //this.setPageInfo(page);

                // 判断数据库类型，确定分页方言（具体要根据个人配置文件的设置）
                Configuration configuration = (Configuration) ReflectUtil.getFieldValue(delegate, "configuration");
                DataSource dataSource = configuration.getEnvironment().getDataSource();
                HikariDataSource ds = (HikariDataSource) ReflectUtil.getFieldValue(dataSource, "defaultTargetDataSource");
                String dataSourceString = (String) ReflectUtil.getFieldValue(ds, "driverClassName");
                if (dataSourceString.contains("mysql")) {
                    this.DIALECT = new MySQLDialect();
                }

                //获取分页Sql语句
                String pageSql = this.DIALECT.getLimitString(page, sql);
                //设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
                ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
            }
        }
        return invocation.proceed();
    }

    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page            Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection      当前的数据库连接
     */
    private void setTotalRecord(PageDto page, MappedStatement mappedStatement, Connection connection) throws Exception {
        //获取对应的BoundSql
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        //获取对应的Sql语句
        String sql = boundSql.getSql();
        //获取计算总记录数的sql语句
        String countSql = this.getCountSql(sql);
        //通过BoundSql获取对应的参数映射
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
        //通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
        //通过connection建立一个countSql对应的PreparedStatement对象。
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            //通过parameterHandler给PreparedStatement对象设置参数
            parameterHandler.setParameters(pstmt);
            //执行获取总记录数的Sql语句。
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                //给当前的参数page对象设置总记录数
                page.setTotalRecord(totalRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException();
            }
        }
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     *
     * @param sql 原sql
     * @return 查询总记录数sql
     */
    private String getCountSql(String sql) {
        int index = new String(sql).toLowerCase().indexOf("from");
        return "select count(*) " + sql.substring(index);
    }

    /**
     * 给page对象补充完整信息
     *
     * @param page page对象
     */
    private void setPageInfo(PageDto page) {
        Integer totalRecord = page.getTotalRecord();
        Integer pageNo = page.getPageNo();
        Integer rows = page.getRows();

        //设置总页数
        Integer totalPage;
        if (totalRecord > rows) {
            if (totalRecord % rows == 0) {
                totalPage = totalRecord / rows;
            } else {
                totalPage = 1 + (totalRecord / rows);
            }
        } else {
            totalPage = 1;
        }
        page.setTotalPage(totalPage);

        //跳转页大于总页数时,默认跳转至最后一页
        if (pageNo > totalPage) {
            pageNo = totalPage;
            page.setPageNo(pageNo);
        }

        //设置是否有前页
        if (pageNo <= 1) {
            page.setHasPrevious(false);
        } else {
            page.setHasPrevious(true);
        }

        //设置是否有后页
        if (pageNo >= totalPage) {
            page.setHasNext(false);
        } else {
            page.setHasNext(true);
        }
    }

    private void initDialect() {

    }

    /**
     * 拦截器对应的封装原始对象的方法
     */
    @Override
    public Object plugin(Object arg0) {

        if (arg0 instanceof StatementHandler) {
            return Plugin.wrap(arg0, this);
        } else {
            return arg0;
        }
    }

    /**
     * 设置注册拦截器时设定的属性
     */
    @Override
    public void setProperties(Properties p) {
//        super.initProperties(p);
    }
}
