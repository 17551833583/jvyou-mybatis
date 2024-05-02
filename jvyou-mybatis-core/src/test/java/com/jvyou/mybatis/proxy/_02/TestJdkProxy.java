package com.jvyou.mybatis.proxy._02;

import com.jvyou.mybatis.proxy.Marry;
import com.jvyou.mybatis.proxy.Spouse;
import org.junit.jupiter.api.Test;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 22:11
 * @Description 测试JDK动态代理
 */
public class TestJdkProxy {

    @Test
    void testJdkProxy() {
        JdkProxy<Marry> marryJdkProxy = new JdkProxy(new Spouse());
        Marry proxy = marryJdkProxy.getProxy();
        proxy.marry();
    }

}
