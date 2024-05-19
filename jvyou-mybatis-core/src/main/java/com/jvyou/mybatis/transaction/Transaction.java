package com.jvyou.mybatis.transaction;

import java.sql.Connection;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/19 21:48
 * @Description 事务
 */
public interface Transaction {

    Connection getConnection();

    void commit() throws Exception;

    void rollback() throws Exception;

    void close() throws Exception;

}
