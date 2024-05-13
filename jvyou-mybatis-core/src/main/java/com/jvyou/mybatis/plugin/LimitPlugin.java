package com.jvyou.mybatis.plugin;

import com.jvyou.mybatis.executor.SqlExecutor;
import com.jvyou.mybatis.mapping.MappedStatement;

import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 20:14
 * @Description 分页插件
 */
@Intercepts({
        @Signature(type = SqlExecutor.class, method = "query", args = {MappedStatement.class, Object.class}),
})
public class LimitPlugin implements PluginInterceptor {


    @Override
    public Object intercept(Invocation invocation) {
        System.out.println("分页插件---开始分页");
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        String sql = ms.getSql();
        ms.setSql(sql + " limit 0,2");
        Object result = invocation.proceed();
        System.out.println("分页插件---结束分页");
        return result;
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
