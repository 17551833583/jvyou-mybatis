package com.jvyou.mybatis.plugin;

import com.jvyou.mybatis.executor.resultset.ResultSetHandler;
import com.jvyou.mybatis.mapping.BoundSql;
import com.jvyou.mybatis.mapping.MappedStatement;

import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 20:15
 * @Description SQL 日志打印插件
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {MappedStatement.class, PreparedStatement.class}),
})
public class SqlLogPlugin implements PluginInterceptor {


    @Override
    public Object intercept(Invocation invocation) {
        System.out.println("日志插件----开始打印日志");
        BoundSql boundSql = ((MappedStatement) invocation.getArgs()[0]).getBoundSql();
        PreparedStatement ps = (PreparedStatement) invocation.getArgs()[1];
        System.out.println(ps);
        Object result = invocation.proceed();
        System.out.println("日志插件----结束打印日志");
        return result;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
