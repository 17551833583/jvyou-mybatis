package com.jvyou.mybatis.proxy._03;

import com.jvyou.mybatis.proxy.Spouse;
import org.junit.jupiter.api.Test;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 23:14
 * @Description 测试 Cglib 动态代理
 */
public class TestCglibProxy {

    @Test
    void testCglibProxy() {
        CglibProxy cglibProxy = new CglibProxy();
        Spouse proxy = cglibProxy.getProxy(Spouse.class);
        proxy.marry();
    }
}
