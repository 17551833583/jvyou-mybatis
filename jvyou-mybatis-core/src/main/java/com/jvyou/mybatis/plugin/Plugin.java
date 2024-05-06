package com.jvyou.mybatis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 14:37
 * @Description 插件代理类
 */
public class Plugin implements InvocationHandler {

    private Object target;
    // 插件拦截器
    PluginInterceptor interceptor;

    public Plugin(Object target, PluginInterceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.interceptor.intercept(new Invocation(this.target, method, args));
    }

    public static <T> T wrap(T target, PluginInterceptor interceptor) {
        Class<?> clazz = target.getClass();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new Plugin(target, interceptor));
    }

}
