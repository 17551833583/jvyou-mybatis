package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.executor.statement.StatementHandler;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;
import lombok.SneakyThrows;

import java.sql.Connection;
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

    @SneakyThrows
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        // 获取数据库链接
        Connection connection = getConnection();
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = getStatement(connection, statementHandler);
        List<T> result = statementHandler.query(statement);
        statement.close();
        connection.close();
        return result;
    }

    @SneakyThrows
    @Override
    public int update(MappedStatement ms, Object parameter) {
        // 获取数据库链接
        Connection connection = getConnection();
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = getStatement(connection, statementHandler);
        int row=statementHandler.update(statement);
        statement.close();
        connection.close();
        return row;
    }

    private Statement getStatement(Connection connection, StatementHandler statementHandler) {

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
    @SneakyThrows
    private Connection getConnection() {
        return configuration.getDataSource().getConnection();
    }

}
