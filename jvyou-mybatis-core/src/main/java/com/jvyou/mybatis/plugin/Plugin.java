package com.jvyou.mybatis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/30 14:37
 * ---description 插件代理类
 */
public class Plugin implements InvocationHandler {

    private final Object target;
    // 插件拦截器
    PluginInterceptor interceptor;

    private final Set<Method> methods;

    public Plugin(Object target, PluginInterceptor interceptor, Set<Method> methods) {
        this.target = target;
        this.interceptor = interceptor;
        this.methods = methods;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methods != null && methods.contains(method)) {
            return this.interceptor.intercept(new Invocation(this.target, method, args));
        } else {
            return method.invoke(this.target, args);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T wrap(T target, PluginInterceptor interceptor) {
        Class<?> clazz = target.getClass();
        Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
        // 遍历拦截器签名
        for (Class<?> key : signatureMap.keySet()) {
            // 拦截器签名里面有匹配
            if (key.isAssignableFrom(clazz)) {
                Set<Method> methods = signatureMap.get(key);
                return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                        clazz.getInterfaces(),
                        new Plugin(target, interceptor, methods));
            }
        }

        return target;
    }

    private static Map<Class<?>, Set<Method>> getSignatureMap(PluginInterceptor interceptor) { 
        Intercepts intercepts = interceptor.getClass().getAnnotation(Intercepts.class);
        Signature[] value = intercepts.value();
        Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
        for (Signature signature : value) {
            Class<?> type = signature.type();
            Method method;
            try {
                method = type.getMethod(signature.method(), signature.args());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            Set<Method> methods = signatureMap.computeIfAbsent(type, k -> new HashSet<>());
            methods.add(method);
        }
        return signatureMap;
    }

}
