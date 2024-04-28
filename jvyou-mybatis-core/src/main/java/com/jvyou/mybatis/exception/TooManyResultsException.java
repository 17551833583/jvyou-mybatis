package com.jvyou.mybatis.exception;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 21:15
 * @Description SQL 语句查询多条结果，但实际上只需要一条
 */
public class TooManyResultsException extends RuntimeException {
    public TooManyResultsException() {
        super("Too many results");
    }

    public TooManyResultsException(String message) {
        super(message);
    }

    public TooManyResultsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyResultsException(Throwable cause) {
        super(cause);
    }
}
