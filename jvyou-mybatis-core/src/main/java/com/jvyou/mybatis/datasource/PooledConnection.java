package com.jvyou.mybatis.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/18 21:10
 * @Description 数据库代理连接
 */
public class PooledConnection implements InvocationHandler {

    private Connection target;

    private Connection proxyConnection;

    private PooledDataSource pooledDataSource;

    public PooledConnection(Connection target, PooledDataSource pooledDataSource) {
        this.target = target;
        this.pooledDataSource = pooledDataSource;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 执行 close 方法的时候将连接归还到连接池中
        if ("close".equals(method.getName())) {
            pooledDataSource.returnConnection(proxyConnection);
        } else {
            return method.invoke(target, args);
        }
        return null;
    }

    public Connection getConnection() {
        proxyConnection = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                this);
        return proxyConnection;
    }

}
