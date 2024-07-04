package com.jvyou.mybatis.proxy._01;

import com.jvyou.mybatis.proxy.Marry;
import com.jvyou.mybatis.proxy.Spouse;
import org.junit.jupiter.api.Test;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/25 21:51
 * ---description 测试静态代理
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
