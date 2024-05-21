package com.jvyou.mybatis.plugin;

import com.jvyou.mybatis.executor.Executor;
import com.jvyou.mybatis.mapping.MappedStatement;

import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 20:14
 * @Description 分页插件
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class}),
})
public class LimitPlugin implements PluginInterceptor {


    @Override
    public Object intercept(Invocation invocation) {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        String sql = ms.getSql();
        if (!sql.contains("limit")) {
            ms.setSql(sql + " limit 0,2");
        }
        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
