package com.jvyou.mybatis.binding;

import com.jvyou.mybatis.annotations.Param;
import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.executor.SimpleSqlExecutor;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.type.IntegerParamHandler;
import com.jvyou.mybatis.type.ParamTypeHandler;
import com.jvyou.mybatis.type.StringParamHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 23:36
 * @Description Mapper 代理增强
 */
public class MapperProxyInvocationHandler implements InvocationHandler, SQLKeyword {



    private final Configuration configuration;

    private final Class<?> mapperClass;

    public MapperProxyInvocationHandler(Configuration configuration, Class<?> mapperClass) {
        this.mapperClass = mapperClass;
        this.configuration = configuration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 从参数名称中获取参数值
        Map<String, Object> paramMap = new HashMap<>();

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(Param.class)) {
                Param annotation = parameter.getAnnotation(Param.class);
                String value = annotation.value();
                paramMap.put(value, args[i]);
            }
        }

        SimpleSqlExecutor simpleSqlExecutor = new SimpleSqlExecutor(configuration);
        MappedStatement mappedStatement = configuration.getMappedStatement(mapperClass.getName() + "." + method.getName());

        return simpleSqlExecutor.query(mappedStatement,paramMap);

    }


}
