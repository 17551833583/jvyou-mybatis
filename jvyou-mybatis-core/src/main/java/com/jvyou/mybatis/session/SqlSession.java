package com.jvyou.mybatis.session;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 19:38
 * @Description 操作增删改查
 */
public interface SqlSession {

    int insert(String statementId, Object parameter);

    int update(String statementId, Object parameter);

    int delete(String statementId, Object parameter);

    <T> T selectOne(String statementId, Object parameter);

    <T> List<T> selectList(String statementId, Object parameter);

    <T> T getMapper(Class<T> mapperClass);

    void close();

    Configuration getConfiguration();

}
