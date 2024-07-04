package com.jvyou.mybatis.executor;


import com.jvyou.mybatis.cache.Cache;
import com.jvyou.mybatis.mapping.MappedStatement;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/5/21 21:25
 * ---description 缓存执行器（装饰者模式）
 */
public class CachingExecutor implements Executor {

    /**
     * 委托执行器，原始的数据库操作还是委托执行器进行执行的
     * 经过CachingExecutor包装之后的执行器会判断是否支持二级缓存，支持二级缓存，CachingExecutor 会在原始的执行器中操作中装饰对缓存的操作
     */
    private final Executor delegate;

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        // 如果缓存在，说明支持二级缓存
        Cache cache = ms.getCache();
        if (cache != null) {
            String key = ms.getCacheKey(parameter);
            Object cacheResult = cache.getObject(key);
            // 缓存中有数据直接返回
            if (cacheResult != null) {
                System.err.println("二级缓存开启,结果命中二级缓存");
                return (List<T>) cacheResult;
            }
            // 缓存中不存在则查询数据库或者一级缓存，查询结果存储在二级缓存里面
            List<Object> queryResult = delegate.query(ms, parameter);
            cache.putObject(key, queryResult);
            System.err.println("二级缓存开启,结果未命中二级缓存，查询数据库");
            return (List<T>) queryResult;
        }
        // 缓存不存在，说明不支持二级缓存，走数据库查询或者一级缓存
        return delegate.query(ms, parameter);
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        // 更新前要清除二级缓存
        Cache cache = ms.getCache();
        if (cache != null) {
            cache.clear();
        }
        return delegate.update(ms, parameter);
    }

    @SneakyThrows
    @Override
    public void commit(boolean required) {
        delegate.commit(required);
    }

    @SneakyThrows
    @Override
    public void rollback(boolean required) {
        delegate.rollback(required);
    }

    @Override
    public void close() {
        delegate.close();
    }
}
