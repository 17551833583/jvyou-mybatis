package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.exception.JvyouMybatisException;
import com.jvyou.mybatis.executor.statement.StatementHandler;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
        // 获取数据库链接
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = getStatement(statementHandler);

        return statementHandler.query(statement);
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = getStatement(statementHandler);
        return statementHandler.update(statement);
    }

    private Statement getStatement(StatementHandler statementHandler) {
        // 获取数据库链接
        Connection connection = getConnection();
        Statement statement = statementHandler.prepare(connection);
        // 填充参数
        statementHandler.parameterize(statement);
        return statement;
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
