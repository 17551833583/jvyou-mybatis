package com.jvyou.mybatis.proxy.staticPro;

import org.junit.jupiter.api.Test;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 21:51
 * @Description 测试静态代理
 */
public class TestStaticProxy {

    @Test
    void testStaticProxy() {
        // 创建代理对象
        Marry target = new Spouse();
        Marry proxy = new WeddingCompany(target);
        // 调用代理对象的方法
        proxy.marry();
    }

}
