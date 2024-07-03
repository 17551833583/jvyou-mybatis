package com.jvyou.mybatis.exception;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/2 17:03
 * @Description XML Mapper 异常
 */
public class XmlMapperException extends RuntimeException {

    public XmlMapperException() {
        super();
    }

    public XmlMapperException(String message) {
        super(message);
    }

    public XmlMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlMapperException(Throwable cause) {
        super(cause);
    }

    protected XmlMapperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
