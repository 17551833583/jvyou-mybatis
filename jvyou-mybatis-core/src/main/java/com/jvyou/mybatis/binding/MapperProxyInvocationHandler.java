package com.jvyou.mybatis.binding;

import com.jvyou.mybatis.annotations.Param;
import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.exception.UnknownSqlCommandException;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.mapping.SqlCommandType;
import com.jvyou.mybatis.session.Configuration;
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

        Configuration configuration = sqlSession.getConfiguration();
        MappedStatement ms = configuration.getMappedStatement(mapperClass.getName() + "." + method.getName());
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        switch (sqlCommandType) {
            case INSERT:
                return sqlSession.insert(ms.getId(), parameters);
            case UPDATE:
                return sqlSession.update(ms.getId(), parameters);
            case DELETE:
                return sqlSession.delete(ms.getId(), parameters);
            case SELECT:
                if (ms.isSelectMany()) {
                    return sqlSession.selectList(ms.getId(), paramMap);
                } else {
                    return sqlSession.selectOne(ms.getId(), paramMap);
                }
        }
        // 如果检查不到 SQL 命令类型，则抛出异常
        throw new UnknownSqlCommandException("未知 SQL 命令类型，或者未检测到 Select、Insert、Update、Delete 注解");
    }


}
