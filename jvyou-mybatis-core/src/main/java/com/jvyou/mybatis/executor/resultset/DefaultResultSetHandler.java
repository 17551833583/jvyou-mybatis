package com.jvyou.mybatis.executor.resultset;

import com.jvyou.mybatis.exception.JvyouMybatisException;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/14 14:05
 * @Description 默认结果集处理器
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private final Configuration configuration;

    public DefaultResultSetHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> handleResultSets(MappedStatement ms, PreparedStatement ps) {
        Class<?> returnType = ms.getResultType();
        ResultSet resultSet = null;
        try {
            resultSet = ps.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List result = new ArrayList();
        Field[] fields = returnType.getDeclaredFields();

        try {
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
            resultSet.close();
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new JvyouMybatisException("Mapping a ResultSet to a query result failed with nested exceptions:\n" + e);
        }
        return result;
    }
}
