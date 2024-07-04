package com.jvyou.mybatis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/28 21:55
 * ---description 新增注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Insert {
    String value();
}
