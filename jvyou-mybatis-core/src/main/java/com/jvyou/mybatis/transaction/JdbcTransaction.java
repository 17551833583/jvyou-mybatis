package com.jvyou.mybatis.transaction;

import com.jvyou.mybatis.session.TransactionIsolationLevel;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/19 21:50
 * @Description JDBC事务管理
 */
public class JdbcTransaction implements Transaction {

    private final DataSource dataSource;

    private Connection connection;

    private final boolean autoCommit;

    private final TransactionIsolationLevel isolationLevel;

    public JdbcTransaction(DataSource dataSource, boolean autoCommit, TransactionIsolationLevel isolationLevel) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
        this.isolationLevel = isolationLevel;
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        if (connection == null) {
            this.connection = dataSource.getConnection();
        }
        // 设置自动提交
        this.connection.setAutoCommit(autoCommit);
        this.connection.setTransactionIsolation(isolationLevel.getLevel());
        return this.connection;
    }

    @SneakyThrows
    @Override
    public void commit() {
        if (connection != null && !autoCommit) {
            connection.commit();
        }
    }

    @SneakyThrows
    @Override
    public void rollback() {
        if (connection != null && !autoCommit) {
            connection.rollback();
        }
    }

    @SneakyThrows
    @Override
    public void close() {
        if (connection != null) {
            connection.close();
        }
    }
}
