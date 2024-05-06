package com.jvyou.mybatis.plugin;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/6 22:41
 * @Description 插件拦截签名
 */
public @interface Signature {
    Class<?> type();
    String method();
}
