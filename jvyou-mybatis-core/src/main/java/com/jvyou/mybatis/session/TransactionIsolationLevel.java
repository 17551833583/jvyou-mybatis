package com.jvyou.mybatis.session;

import java.sql.Connection;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/20 13:07
 * @Description 事务隔离级别
 */
public enum TransactionIsolationLevel {

    /**
     * 不支持事务。使用这个级别时，数据库操作不会受到事务管理
     */
    NONE(Connection.TRANSACTION_NONE),

    /**
     * 指示可能发生脏读取、不可重复读取和幻像读取。
     * 此级别允许在提交对一个事务更改的行之前，由另一个事务读取该行（“脏读取”）。
     * 如果回滚任何更改，则第二个事务将检索无效行。
     */
    READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),

    /**
     * 指示防止脏读取的常量;可能会发生不可重复的读取和幻像读取。
     * 此级别仅禁止事务读取包含未提交更改的行。
     */
    READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),

    /**
     * 指示防止脏读取和不可重复读取;可能会发生幻像读取。
     * 此级别禁止事务读取包含未提交更改的行。
     * 并且还禁止一个事务读取一行，第二个事务更改该行，
     * 第一个事务重新读取该行，第二次获得不同的值（“不可重复读取”）。
     */
    REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),

    /**
     * 指示防止脏读取、不可重复读取和幻像读取。
     * 此级别包括以下 TRANSACTION_REPEATABLE_READ 情况的禁止，并
     * 进一步禁止以下情况：一个事务读取满足条件 WHERE 的所有行，第二个事务插入满足该 WHERE 条件的行，
     * 并且第一个事务针对同一条件重新读取，在第二次读取中检索额外的“幻像”行。
     */
    SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);


    private final int level;

    private TransactionIsolationLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}