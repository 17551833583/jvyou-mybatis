package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 17:40
 * @Description SQL 执行器
 */
public interface SqlExecutor {

    <T> List<T> query(MappedStatement ps, Object parameter);

    int update(MappedStatement ms, Object parameter);
}
