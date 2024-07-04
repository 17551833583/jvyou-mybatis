package com.jvyou.mybatis.session;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/28 19:38
 * ---description 操作增删改查
 */
public interface SqlSession {

    /**
     * 插入
     *
     * @param statementId 语句id
     * @param parameter   参数
     * @return 影响行数
     */
    int insert(String statementId, Object parameter);

    /**
     * 更新
     *
     * @param statementId 语句id
     * @param parameter   参数
     * @return 影响行数
     */
    int update(String statementId, Object parameter);

    /**
     * 删除
     *
     * @param statementId 语句id
     * @param parameter   参数
     * @return 影响行数
     */
    int delete(String statementId, Object parameter);

    /**
     * 单条查询
     *
     * @param statementId 语句id
     * @param parameter   参数
     * @param <T>         实体类型
     * @return 结果
     */
    <T> T selectOne(String statementId, Object parameter);

    /**
     * 批量查询
     *
     * @param statementId 语句id
     * @param parameter   参数
     * @param <T>         实体类型
     * @return 结果集
     */
    <T> List<T> selectList(String statementId, Object parameter);

    /**
     * 批量查询
     *
     * @param statementId 语句id
     * @param <E>         实体类型
     * @return 结果集
     */
    <E> List<E> selectList(String statementId);

    /**
     * 获取 Mapper 对象
     *
     * @param mapperClass mapper 的 class类型
     * @param <T>         mapper 的 class类型
     * @return mapper 对象
     */
    <T> T getMapper(Class<T> mapperClass);

    /**
     * 关闭 Session
     */
    void close();

    /**
     * 提交事务
     */
    void commit();

    /**
     * 回滚事务
     */
    void rollback();

    /**
     * 获取配置
     *
     * @return 环境配置
     */
    Configuration getConfiguration();

}
