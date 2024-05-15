package com.jvyou.mybatis.executor.statement;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/15 22:22
 * @Description 语句处理器接口
 */
public interface StatementHandler {

    Statement prepare(Connection connection);

    void parameterize(Statement statement);

    <T> T query(Statement statement);

    int update(Statement statement);

}
