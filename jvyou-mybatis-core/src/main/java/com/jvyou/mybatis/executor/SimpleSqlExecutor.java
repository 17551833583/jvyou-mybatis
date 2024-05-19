package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.executor.statement.StatementHandler;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.transaction.Transaction;
import lombok.SneakyThrows;

import java.sql.Connection;
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

    private final Transaction transaction;

    public SimpleSqlExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
    }

    @SneakyThrows
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        // 获取数据库链接
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = getStatement(statementHandler);
        List<T> result = statementHandler.query(statement);
        statement.close();
        return result;
    }

    @SneakyThrows
    @Override
    public int update(MappedStatement ms, Object parameter) {
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = getStatement(statementHandler);
        int row = statementHandler.update(statement);
        statement.close();
        return row;
    }

    @SneakyThrows
    @Override
    public void commit(boolean required) throws SQLException {
        transaction.commit();
    }

    @SneakyThrows
    @Override
    public void rollback(boolean required) throws SQLException {
        transaction.rollback();
    }

    @SneakyThrows
    @Override
    public void close() {
        transaction.close();
    }

    private Statement getStatement(StatementHandler statementHandler) {
        Connection connection = this.transaction.getConnection();
        Statement statement = statementHandler.prepare(connection);
        // 填充参数
        statementHandler.parameterize(statement);
        return statement;
    }


}
