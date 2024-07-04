package com.jvyou.mybatis.binding;

import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/25 23:31
 * ---description Mapper 代理对象工厂类
 */
public class MapperProxyFactory {

    /**
     * 获取 Mapper 接口的代理对象
     *
     * @param mapperClass mapper 接口类型
     * @return 获取 Mapper 接口的代理对象
     */
    public <T> T getProxy(Class<T> mapperClass, SqlSession sqlSession) {

        Object o = Proxy.newProxyInstance(
                mapperClass.getClassLoader(),
                new Class[]{mapperClass},
                new MapperProxyInvocationHandler(sqlSession,mapperClass)
        );
        // 使用 cast 转换类型而不是类型强制转换可以避免警告
        // cast 类型转换提供了更强的类型检查机，处理泛型时更具灵活性
        return mapperClass.cast(o);
    }
}
