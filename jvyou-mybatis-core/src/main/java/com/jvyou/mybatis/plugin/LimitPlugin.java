package com.jvyou.mybatis.plugin;

import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 20:14
 * @Description 分页插件
 */
public class LimitPlugin implements PluginInterceptor {


    @Override
    public Object intercept(Invocation invocation) {
        System.out.println("分页插件---开始分页");
        Object result = invocation.proceed();
        System.out.println("分页插件---结束分页");
        return result + "--分页插件处理结果";
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
