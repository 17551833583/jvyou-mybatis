package com.jvyou.mybatis.binding;

import com.jvyou.mybatis.annotations.Param;
import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.exception.UnknownSqlCommandException;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.mapping.SqlCommandType;
import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.session.SqlSession;
import com.jvyou.mybatis.xml.DynamicContext;

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
        // key 值为 Param 注解的 value
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
        // 动态节点不为空，需要解析动态SQL
        if (ms.getSqlSource() != null && (ms.getSql() == null || ms.getSql().trim().length() == 0)) {
            DynamicContext context = new DynamicContext((Map<String, Object>) paramMap);
            ms.getSqlSource().apply(context);
            String sql = context.getSql()
                    .replace("\n", " ")  // 移除换行符
                    .replaceAll("\\s+", " "); // 移除多余的空格
            ms.setSql(sql);
        }
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        Class<?> resultType = ms.getResultType();

        switch (sqlCommandType) {
            case INSERT:
                return convertType(sqlSession.insert(ms.getId(), paramMap), resultType);
            case UPDATE:
                return convertType(sqlSession.update(ms.getId(), paramMap), resultType);
            case DELETE:
                return convertType(sqlSession.delete(ms.getId(), paramMap), resultType);
            case SELECT:
                if (ms.isSelectMany()) {
                    return sqlSession.selectList(ms.getId(), paramMap);
                } else {
                    return sqlSession.selectOne(ms.getId(), paramMap);
                }
        }
        // 如果检查不到 SQL 命令类型，则抛出异常
        throw new UnknownSqlCommandException("Unknown SQL command type，Or not detected Select、Insert、Update、Delete annotation");
    }


    private Object convertType(Object value, Class<?> type) {
        if (value == null || type == void.class) {
            return null;
        }
        if (type == Integer.class || type == int.class) {
            return value;
        }
        if (type == Long.class || type == long.class) {
            return Long.parseLong(value.toString());
        }
        if (type == Short.class || type == short.class) {
            return Short.parseShort(value.toString());
        }
        if (type == Float.class || type == float.class) {
            return Float.parseFloat(value.toString());
        }
        if (type == Boolean.class || type == boolean.class) {
            return Integer.parseInt(value.toString()) >= 1;
        }
        return value;
    }
}
