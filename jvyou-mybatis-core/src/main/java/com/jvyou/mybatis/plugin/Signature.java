package com.jvyou.mybatis.plugin;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/5/6 22:41
 * ---description 插件拦截签名
 */
public @interface Signature {
    Class<?> type();

    String method();

    // TODO:优化默认参数
    Class<?>[] args();
}
