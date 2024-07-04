package com.jvyou.mybatis.session.defaults;

import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.session.SqlSession;
import com.jvyou.mybatis.session.SqlSessionFactory;
import com.jvyou.mybatis.session.TransactionIsolationLevel;
import com.jvyou.mybatis.transaction.JdbcTransaction;
import com.jvyou.mybatis.transaction.Transaction;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/28 21:36
 * ---description 默认 SqlSession 工厂实现类
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        return openSessionFromDataSource(TransactionIsolationLevel.DEFAULT, autoCommit);
    }

    @Override
    public SqlSession openSession(TransactionIsolationLevel level) {
        return openSessionFromDataSource(level, false);
    }

    private SqlSession openSessionFromDataSource(TransactionIsolationLevel level, boolean autoCommit) {
        Transaction transaction = new JdbcTransaction(configuration.getDataSource(), autoCommit, level);
        return new DefaultSqlSession(configuration, configuration.newSqlExecutor(transaction));
    }
}
