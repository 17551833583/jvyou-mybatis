package com.jvyou.mybatis.binding;

import com.jvyou.mybatis.annotations.Param;
import com.jvyou.mybatis.annotations.Select;
import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.parser.GenericTokenParser;
import com.jvyou.mybatis.parser.ParameterMappingTokenHandler;
import com.jvyou.mybatis.type.IntegerParamHandler;
import com.jvyou.mybatis.type.ParamTypeHandler;
import com.jvyou.mybatis.type.StringParamHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // 获取结果集
        ResultSet resultSet = ps.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + "--" + resultSet.getInt("age"));
        }
        // 5.关闭数据库链接
        resultSet.close();
        ps.close();
        connection.close();

        return null;
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
}
