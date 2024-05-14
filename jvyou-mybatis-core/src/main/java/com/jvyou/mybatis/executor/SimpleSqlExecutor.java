package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.exception.JvyouMybatisException;
import com.jvyou.mybatis.mapping.BoundSql;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 17:43
 * @Description 简单 SQL 执行器
 */
public class SimpleSqlExecutor implements SqlExecutor {

    private Configuration configuration;

    public SimpleSqlExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        // 获取 Mapper 方法的返回值类型
        Class<?> returnType = ms.getResultType();
        // 获取数据库链接
        Connection connection = getConnection();

        List<T> result;
        try {
            PreparedStatement ps = execute(connection, ms, parameter);
            // 获取结果集
            // 处理结果集
            result = configuration.newResultSetHandler().handleResultSets(ms, ps);
            // 关闭数据库链接
            ps.close();
            connection.close();

        } catch (SQLException e) {
            throw new JvyouMybatisException("An error occurred in the execution of the query operation, and the nested exception was：" + e);
        }
        return result;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        // 获取数据库链接
        Connection connection = getConnection();
        // 修改的行数
        int row;
        try {
            PreparedStatement ps = execute(connection, ms, parameter);
            row = ps.getUpdateCount();
            // 关闭数据库链接
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new JvyouMybatisException("An error occurred in the execution of the update operation, and the nested exception was：" + e);
        }
        return row;
    }

    private PreparedStatement execute(Connection connection, MappedStatement ms, Object parameter) throws SQLException {
        BoundSql boundSql = ms.getBoundSql();
        PreparedStatement ps = connection.prepareStatement(boundSql.getParsedSql());
        // 代理方法传递过来的真实参数，key值为 Param 注解 value 的值
        Map<String, Object> paramMap = parameter == null ? null : (Map<String, Object>) parameter;
        // 填充参数
        populateParameters(boundSql.getParamNames(), ps, paramMap);
        ps.execute();
        return ps;
    }

    /**
     * 向 PreparedStatement 填充参数
     *
     * @param params   参数名称列表，解析 SQL 语句时候从占位符中提取
     * @param ps       PreparedStatement 对象
     * @param paramMap 真实参数，key 为 Param 注解的 value 值
     */
    private void populateParameters(List<String> params, PreparedStatement ps, Map<String, Object> paramMap) {
        if (paramMap != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                String paramName = params.get(i);
                Object value;
                // 如果由 “.” 号，说明是 Mapper 方法传递过来的对象
                if (paramName.contains(".")) {
                    String[] paramNames = paramName.split("\\.");
                    value = paramMap.get(paramNames[0]);
                    for (int j = 1; j < paramNames.length; j++) {
                        value = getFieldValue(paramNames[j], value.getClass(), value);
                    }
                } else {
                    value = paramMap.get(paramName);
                }
                try {
                    configuration.getParamTypeHandler(value.getClass()).setParameter(ps, i + 1, value);
                } catch (SQLException e) {
                    throw new JvyouMybatisException("Populating the value passed by the method to PreparedStatement error, nested exception is:\n" + e);
                }
            }
        }
    }

    /**
     * 获取数据库链接
     *
     * @return 数据库链接
     */
    private Connection getConnection() {
        // 加载数据库驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new JvyouMybatisException("Loading the database driver failed with nested exceptions:\n" + e);
        }
        // 获取数据库链接
        String url = "jdbc:mysql://127.0.0.1:3306/jvyou-mybatis?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        String username = "root";
        String password = "123456";
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new JvyouMybatisException("The get database connection failed, and the nested exception was:\n" + e);
        }
    }

    private Object getFieldValue(String fieldName, Class<?> clazz, Object obj) {
        if (obj == null || obj instanceof Class) {
            return null;
        }
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理结果集
     *
     * @param resultSet  结果集对象
     * @param returnType 返回值类型
     * @return 将结果集里面的内容映射为指定类型的集合，默认根据字段的名称映射
     */
    /*private  List handleResult(ResultSet resultSet, Class<?> returnType) {
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
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new JvyouMybatisException("Mapping a ResultSet to a query result failed with nested exceptions:\n" + e);
        }
        return result;
    }*/

}
