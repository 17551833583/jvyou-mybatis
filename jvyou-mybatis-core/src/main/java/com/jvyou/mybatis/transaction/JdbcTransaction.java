package com.jvyou.mybatis.transaction;

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

    public JdbcTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        this.connection = dataSource.getConnection();
        // 设置自动提交
        this.connection.setAutoCommit(autoCommit);
        return this.connection;
    }

    @Override
    public void commit() throws Exception {
        if (connection != null && !autoCommit) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws Exception {
        if (connection != null && !autoCommit) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
