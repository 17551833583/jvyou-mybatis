package com.jvyou.mybatis.proxy._02;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/25 22:01
 * ---description JDK 动态代理类
 */
public class JdkProxy<T> implements InvocationHandler {

    private final T target;

    public JdkProxy(T target) {
        this.target = target;
    }

    public T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK 动态代理类开始...");
        Object result = method.invoke(target, args);
        System.out.println("JDK 动态代理类结束...");
        return result;
    }
}
