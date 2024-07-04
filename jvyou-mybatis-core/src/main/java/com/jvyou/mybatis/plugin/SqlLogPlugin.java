package com.jvyou.mybatis.plugin;

import com.jvyou.mybatis.executor.statement.StatementHandler;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/30 20:15
 * ---description SQL 日志打印插件
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = Statement.class),
        @Signature(type = StatementHandler.class, method = "update", args = Statement.class),
})
public class SqlLogPlugin implements PluginInterceptor {


    @Override
    public Object intercept(Invocation invocation) {

        PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
        String sql = ps.toString().replace("com.mysql.cj.jdbc.ClientPreparedStatement:", "").trim();
        System.err.println("SQL:" + sql);
        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
