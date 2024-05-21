package com.jvyou.mybatis.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/21 13:46
 * @Description 永久缓存
 */
public class PerpetualCache implements Cache {

    private final String id;

    private final Map<String, Object> cache = new HashMap<>();

    public PerpetualCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(String key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(String key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
