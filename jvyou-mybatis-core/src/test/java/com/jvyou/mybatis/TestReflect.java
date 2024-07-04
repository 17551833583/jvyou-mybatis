package com.jvyou.mybatis;

import org.junit.jupiter.api.Test;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/25 22:22
 * ---description 测试反射
 */
public class TestReflect {

    @Test
    void testReflect() throws Exception {
        // 获取类对象
        Class<?> clazz = Class.forName("com.jvyou.mybatis.TestJdbc");
        // 获取构造方法
        // 获取无参构造方法
        // Constructor<?> constructor = clazz.getConstructor();
        // 获取有参构造方法
        // Constructor<?> constructor = clazz.getConstructor(String.class);
        // 获取所有构造方法
        // Constructor<?>[] constructors = clazz.getConstructors();
    }

}
