package com.jvyou.mybatis.session;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 21:35
 * @Description SqlSession 工厂类
 */
public interface SqlSessionFactory {

    /**
     * 获取 SqlSession，默认不自动提交事务
     *
     * @return SqlSession
     */
    default SqlSession openSession() {
        return openSession(false);
    }

    /**
     * 获取 SqlSession
     *
     * @param autoCommit 是否自动提交
     * @return SqlSession
     */
    SqlSession openSession(boolean autoCommit);

    SqlSession openSession(TransactionIsolationLevel level);

}
