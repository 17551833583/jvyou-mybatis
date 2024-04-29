package com.jvyou.mybatis.exception;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/29 14:30
 * @Description 未知 SQL 命令
 */
public class UnknownSqlCommandException extends RuntimeException{

    public UnknownSqlCommandException() {
        super("未知 SQL 命令类型");
    }

    public UnknownSqlCommandException(String message) {
        super(message);
    }

    public UnknownSqlCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownSqlCommandException(Throwable cause) {
        super(cause);
    }

}
