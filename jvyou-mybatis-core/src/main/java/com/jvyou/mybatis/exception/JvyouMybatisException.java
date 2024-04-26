package com.jvyou.mybatis.exception;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 13:33
 * @Description 橘柚 Mybatis 异常
 */
public class JvyouMybatisException extends RuntimeException {

    public JvyouMybatisException() {
        super();
    }

    public JvyouMybatisException(String message) {
        super(message);
    }

    public JvyouMybatisException(String message, Throwable cause) {
        super(message, cause);
    }

    public JvyouMybatisException(Throwable cause) {
        super(cause);
    }

    protected JvyouMybatisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
