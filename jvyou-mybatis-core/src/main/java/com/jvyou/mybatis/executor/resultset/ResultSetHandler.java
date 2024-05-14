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

    <T> List<T> handleResultSets(MappedStatement ms, PreparedStatement ps);

}
