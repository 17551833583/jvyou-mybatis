package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.exception.JvyouMybatisException;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.parser.GenericTokenParser;
import com.jvyou.mybatis.parser.ParameterMappingTokenHandler;
import com.jvyou.mybatis.session.Configuration;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 17:43
 * @Description 简单 SQL 执行器
 */
public class SimpleSqlExecutor implements SqlExecutor, SQLKeyword {

    private Configuration configuration;

    public SimpleSqlExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        // 获取原始的 SQL
        String originalSql = ms.getSql();
        // 获取 Mapper 方法的返回值类型
        Class<?> returnType = ms.getResultType();
        // 解析 SQL
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser(SQL_OPEN_TOKEN, SQL_CLOSE_TOKEN, tokenHandler);
        String parsedSql = genericTokenParser.parse(originalSql);

        List result = new ArrayList();
        try {
            // 获取数据库链接
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(parsedSql);
            // 获取参数名称
            List<String> params = tokenHandler.getParams();

            Map<String, Object> paramMap = parameter == null ? null : (Map<String, Object>) parameter;

            // 填充参数
            if (paramMap != null && params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    String param = params.get(i);
                    Object value = paramMap.get(param);
                    configuration.getParamTypeHandler(value.getClass()).setParameter(ps, i + 1, value);
                }
            }
            ps.execute();
            // 获取结果集
            ResultSet resultSet = ps.getResultSet();

            Field[] fields = returnType.getDeclaredFields();

            while (resultSet.next()) {
                Object obj = returnType.newInstance();
                for (Field field : fields) {
                    // 获取字段名称
                    String fieldName = field.getName();
                    // 获取字段值
                    Object fieldValue = configuration.getParamTypeHandler(field.getType()).getResult(resultSet, fieldName);
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
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new JvyouMybatisException("An error occurred in the execution of the query operation, and the nested exception was：" + e);
        }
        return result;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        // 获取原始的 SQLm
        String originalSql = ms.getSql();

        // 解析 SQL
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser(SQL_OPEN_TOKEN, SQL_CLOSE_TOKEN, tokenHandler);
        String parsedSql = genericTokenParser.parse(originalSql);
        // 修改的行数
        int row = 0;
        try {
            // 获取数据库链接
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(parsedSql);
            // 获取参数名称
            List<String> params = tokenHandler.getParams();

            Map<String, Object> paramMap = parameter == null ? null : (Map<String, Object>) parameter;

            // 填充参数
            if (paramMap != null && params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    String param = params.get(i);
                    Object value = paramMap.get(param);
                    configuration.getParamTypeHandler(value.getClass()).setParameter(ps, i + 1, value);
                }
            }
            row = ps.executeUpdate();

            // 5.关闭数据库链接
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new JvyouMybatisException("An error occurred in the execution of the query operation, and the nested exception was：" + e);
        }
        return row;
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
