package com.jvyou.mybatis.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/5/6 22:40
 * ---description 插件拦截注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Intercepts {
    Signature[] value();
}
