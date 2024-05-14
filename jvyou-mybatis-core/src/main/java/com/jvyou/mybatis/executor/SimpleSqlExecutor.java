package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.exception.JvyouMybatisException;
import com.jvyou.mybatis.mapping.BoundSql;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 17:43
 * @Description 简单 SQL 执行器
 */
public class SimpleSqlExecutor implements SqlExecutor {

    private final Configuration configuration;

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
        configuration.newParamTypeHandler().setParameters(boundSql.getParamNames(), ps, paramMap);
        ps.execute();
        return ps;
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

}
