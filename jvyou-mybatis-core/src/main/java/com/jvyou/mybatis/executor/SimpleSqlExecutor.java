package com.jvyou.mybatis.executor;

import com.jvyou.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 17:43
 * @Description 简单 SQL 执行器
 */
public class SimpleSqlExecutor implements SqlExecutor{
    @Override
    public <T> List<T> query(MappedStatement ps, Object parameter) {
        return null;
    }

    @Override
    public int update(MappedStatement ps, Object parameter) {
        return 0;
    }
}
