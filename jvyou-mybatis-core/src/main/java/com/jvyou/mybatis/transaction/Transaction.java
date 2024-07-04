package com.jvyou.mybatis.transaction;

import java.sql.Connection;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/5/19 21:48
 * ---description 事务
 */
public interface Transaction {

    /**
     * 从数据源获取连接，并将连接开启是否自动提交
     *
     * @return 数据库连接
     */
    Connection getConnection();

    /**
     * 提交事务
     */
    void commit();

    /**
     * 回滚事务
     */
    void rollback();

    /**
     * 关闭事务
     */
    void close();

}
