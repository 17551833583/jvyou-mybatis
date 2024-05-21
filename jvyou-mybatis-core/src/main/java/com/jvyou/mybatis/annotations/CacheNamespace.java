package com.jvyou.mybatis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/21 21:23
 * @Description 二级缓存开启注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CacheNamespace {
}
