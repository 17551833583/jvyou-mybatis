package com.jvyou.mybatis.plugin;

import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/30 14:53
 * ---description 插件拦截器
 */
public interface PluginInterceptor {

    Object intercept(Invocation invocation);

    void setProperties(Properties properties);

    /**
     * 插件包装
     *
     * @param target 被包装的对象
     * @param <T>    被包装对象的类型
     * @return 被包装后的代理对象
     */
    default <T> T plugin(T target) {
        return Plugin.wrap(target, this);
    }

}
