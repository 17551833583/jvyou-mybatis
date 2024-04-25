package com.jvyou.mybatis.proxy._03;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 23:15
 * @Description 创建 Cglib 代理类
 */
public class CglibProxy implements MethodInterceptor {

    public <T> T getProxy(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib 代理类代理开始。。。");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("Cglib 代理类代理结束。。。");
        return result;
    }
}
