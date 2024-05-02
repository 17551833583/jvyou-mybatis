package com.jvyou.mybatis.plugin;

import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 14:53
 * @Description 插件拦截器
 */
public interface PluginInterceptor {

    Object intercept(Invocation invocation);

    void setProperties(Properties properties);

}
