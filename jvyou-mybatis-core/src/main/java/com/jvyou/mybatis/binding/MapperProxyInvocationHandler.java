package com.jvyou.mybatis.binding;

import com.jvyou.mybatis.annotations.Param;
import com.jvyou.mybatis.annotations.Select;
import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.parser.GenericTokenParser;
import com.jvyou.mybatis.parser.ParameterMappingTokenHandler;
import com.jvyou.mybatis.type.IntegerParamHandler;
import com.jvyou.mybatis.type.ParamTypeHandler;
import com.jvyou.mybatis.type.StringParamHandler;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 23:36
 * @Description Mapper 代理增强
 */
public class MapperProxyInvocationHandler implements InvocationHandler, SQLKeyword {

    @SuppressWarnings("rawtypes")
    private final Map<Class, ParamTypeHandler> paramTypeHandlerMap = new HashMap<>();

    public MapperProxyInvocationHandler() {
        paramTypeHandlerMap.put(Integer.class, new IntegerParamHandler());
        paramTypeHandlerMap.put(String.class, new StringParamHandler());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Select selectAnnotation = method.getAnnotation(Select.class);
        String originalSql = selectAnnotation.value();

        // 解析 SQL
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser(SQL_OPEN_TOKEN, SQL_CLOSE_TOKEN, tokenHandler);
        String parsedSql = genericTokenParser.parse(originalSql);
        // 获取数据库链接
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(parsedSql);
        // 获取参数名称
        List<String> params = tokenHandler.getParams();
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
        // 填充参数
        for (int i = 0; i < params.size(); i++) {
            String param = params.get(i);
            Object value = paramMap.get(param);
            paramTypeHandlerMap.get(value.getClass()).setParameter(ps, i + 1, value);
        }

        ps.execute();

        // 获取 Mapper 方法的返回值类型
        Class<?> returnType = getMethodReturnType(method);

        // 获取结果集
        ResultSet resultSet = ps.getResultSet();
        List result = new ArrayList();

        Field[] fields = returnType.getDeclaredFields();

        while (resultSet.next()) {
            Object obj = returnType.newInstance();
            for (Field field : fields) {
                // 获取字段名称
                String fieldName = field.getName();
                // 获取字段值
                Object fieldValue = paramTypeHandlerMap.get(field.getType()).getResult(resultSet, fieldName);
                // 设置字段值
                field.setAccessible(true);
                field.set(obj, fieldValue);
            }
            result.add(obj);
        }
        // 5.关闭数据库链接
        resultSet.close();
        ps.close();
        connection.close();

        // 返回值是数组类型
        if (returnType.isArray()) {
            return result.toArray();
        }
        // 返回值是集合类型
        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            return result;
        }
        // 返回值是单个对象
        return result.get(0);
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库链接
        String url = "jdbc:mysql://127.0.0.1:3306/jvyou-mybatis?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        String username = "root";
        String password = "123456";
        return DriverManager.getConnection(url, username, password);
    }


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
    private Class<?> getMethodReturnType(Method method) {
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
