package com.jvyou.mybatis.session.defaults;

import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.session.SqlSession;
import com.jvyou.mybatis.session.SqlSessionFactory;
import com.jvyou.mybatis.transaction.JdbcTransaction;
import com.jvyou.mybatis.transaction.Transaction;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 21:36
 * @Description 默认 SqlSession 工厂实现类
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        Transaction transaction = new JdbcTransaction(configuration.getDataSource(), autoCommit);
        return new DefaultSqlSession(configuration, configuration.newSqlExecutor(transaction));
    }
}
