package com.jvyou.mybatis.binding;

import com.jvyou.mybatis.annotations.Param;
import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.session.SqlSession;

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


    private final SqlSession sqlSession;

    private final Class<?> mapperClass;

    public MapperProxyInvocationHandler(SqlSession sqlSession, Class<?> mapperClass) {
        this.mapperClass = mapperClass;
        this.sqlSession = sqlSession;
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

        return sqlSession.selectList(mapperClass.getName() + "." + method.getName(), paramMap);

    }


}
