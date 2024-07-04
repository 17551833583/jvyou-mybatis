package com.jvyou.mybatis.datasource;

import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/5/18 20:54
 * ---description 数据库连接池
 */
public class PooledDataSource implements DataSource {

    private final int POOL_SIZE = 10;

    private LinkedBlockingDeque<Connection> pool = new LinkedBlockingDeque<>(POOL_SIZE);


    @SneakyThrows
    public PooledDataSource(String username, String password, String driverClass, String url) {
        Connection connection = DriverManager.getConnection(url, username, password);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(new PooledConnection(connection, this).getConnection());
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return pool.poll();
    }

    public void returnConnection(Connection connection) {
        this.pool.add(connection);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
