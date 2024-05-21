package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.cache.Cache;
import com.jvyou.mybatis.cache.PerpetualCache;
import com.jvyou.mybatis.executor.statement.StatementHandler;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.transaction.Transaction;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 17:43
 * @Description 简单 SQL 执行器
 */
public class SimpleExecutor implements Executor {

    private final Configuration configuration;

    private final Transaction transaction;

    /**
     * 会话级别的缓存
     * 一次会话会创建一个执行器，每个执行器会有一个本地缓存
     */
    private final Cache loaclCache;

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
        this.loaclCache = new PerpetualCache("LocalCache");
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        String cacheKey = ms.getCacheKey(parameter);
        Object list = loaclCache.getObject(cacheKey);
        if (list != null) {
            return (List<T>) list;
        }
        // 获取数据库链接
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = getStatement(statementHandler);
        List<T> result = statementHandler.query(statement);
        statement.close();
        loaclCache.putObject(cacheKey, result);
        return result;
    }

    @SneakyThrows
    @Override
    public int update(MappedStatement ms, Object parameter) {
        // 执行更新操作必须清除缓存，防止缓存数据与数据库数据不一致
        loaclCache.clear();
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = getStatement(statementHandler);
        int row = statementHandler.update(statement);
        statement.close();
        return row;
    }

    @SneakyThrows
    @Override
    public void commit(boolean required) {
        transaction.commit();
    }

    @SneakyThrows
    @Override
    public void rollback(boolean required) {
        transaction.rollback();
    }

    @SneakyThrows
    @Override
    public void close() {
        transaction.close();
    }

    private Statement getStatement(StatementHandler statementHandler) {
        Connection connection = this.transaction.getConnection();
        Statement statement = statementHandler.prepare(connection);
        // 填充参数
        statementHandler.parameterize(statement);
        return statement;
    }


}
