package com.jvyou.mybatis.plugin;

import com.jvyou.mybatis.executor.statement.StatementHandler;
import com.jvyou.mybatis.mapping.BoundSql;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/30 20:14
 * ---description 分页插件
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
})
public class LimitPlugin implements PluginInterceptor {


    @Override
    public Object intercept(Invocation invocation) {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getParsedSql();
        if (!sql.contains("limit")) {
            sql = sql + " limit 0,2";
            boundSql.setParsedSql(sql.replaceAll("\\s+", " "));
        }
        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
