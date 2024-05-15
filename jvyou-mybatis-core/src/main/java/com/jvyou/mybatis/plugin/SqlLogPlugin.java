package com.jvyou.mybatis.plugin;

import com.jvyou.mybatis.executor.statement.StatementHandler;
import com.jvyou.mybatis.mapping.MappedStatement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 20:15
 * @Description SQL 日志打印插件
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = Statement.class),
        @Signature(type = StatementHandler.class, method = "update", args = Statement.class),
})
public class SqlLogPlugin implements PluginInterceptor {


    @Override
    public Object intercept(Invocation invocation) {
        System.out.println("日志插件----开始打印日志");
        PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
        System.out.println(ps);
        Object result = invocation.proceed();
        System.out.println("日志插件----结束打印日志");
        return result;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
