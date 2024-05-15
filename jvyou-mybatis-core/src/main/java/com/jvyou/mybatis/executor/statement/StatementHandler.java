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

    /**
     * 根据连接对象创建预编译 SQL 语句对象
     * 适用于执行带有占位符（?）的SQL语句，这些占位符可以在执行前用具体的参数值替换，可以提高SQL语句的执行效率，并且可以防止SQL注入攻击。
     *
     * @param connection 数据库连接对象
     * @return PreparedStatement
     */
    Statement prepare(Connection connection);

    /**
     * 参数化SQL语句
     * 通过参数处理器（ParameterHandler）给预编译SQL语句对象设置参数
     *
     * @param statement 预编译SQL语句对象
     */
    void parameterize(Statement statement);

    /**
     * 执行SQL语句，通过结果集处理器（ResultSetHandler）处理结果集
     *
     * @param statement 预编译SQL语句对象
     * @param <T>       查询返回类型
     * @return 结果集
     */
    <T> T query(Statement statement);

    /**
     * 执行SQL语句，通过PreparedStatement获取更新的行数
     *
     * @param statement 预编译SQL语句对象
     * @return 更新的行数
     */
    int update(Statement statement);

}
