package com.jvyou.mybatis.utils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/27 22:17
 * @Description
 */
public class TypeUtils {

    /**
     * 获取方法返回值的类型。
     * 这个方法能够处理普通类型、参数化类型以及数组等返回类型。对于参数化类型，会尝试返回第一个泛型参数的Class类型，
     * 如果无法获取到，则返回参数化类型的原始类型。对于非参数化类型，直接返回其Class类型。
     * 如果返回类型为数组，则该方法当前返回null，可根据需求进行相应处理。
     *
     * @param method 需要获取返回类型的方法对象
     * @return 返回值的类型。如果是参数化类型，返回第一个泛型参数的Class类型或参数化类型的原始类型；
     * 如果是普通类型，返回其Class类型；如果是数组等其他情况，当前返回null。
     */
    public static Class<?> getMethodReturnType(Method method) {
        Type returnType = method.getGenericReturnType();

        if (returnType instanceof ParameterizedType) {
            // 处理参数化类型
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0 && actualTypeArguments[0] instanceof Class) {
                // 返回第一个泛型参数的Class类型
                return (Class<?>) actualTypeArguments[0];
            } else {
                // 如果无法获取到泛型参数的Class类型，则返回原始类型
                return (Class<?>) parameterizedType.getRawType();
            }
        } else if (returnType instanceof Class) {
            // 处理普通类型
            return (Class<?>) returnType;
        } else {
            // 处理其他情况，比如数组等
            return null; // 或者根据需求进行相应处理
        }
    }

}
