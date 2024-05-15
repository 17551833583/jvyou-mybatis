package com.jvyou.mybatis.executor.resultset;

import com.jvyou.mybatis.mapping.MappedStatement;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/14 14:03
 * @Description 结果集处理器
 */
public interface ResultSetHandler {

    /**
     * 处理结果集，将结果集（ResultSet）映射成实体类
     *
     * @param ms  MappedStatement 映射语句对象
     * @param ps  PreparedStatement 预编译语句
     * @param <T> 实体类集合
     * @return 实体类集合
     */
    <T> List<T> handleResultSets(MappedStatement ms, PreparedStatement ps);

}
