package com.jvyou.mybatis.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/1 1:15
 * @Description 动态上下文
 */
public class DynamicContext {

    private final StringBuilder sqlBuilder = new StringBuilder();

    private final Map<String, Object> bindings = new HashMap<>();

    public String getSql() {
        return sqlBuilder.toString();
    }

    public void appendSql(String sql) {
        sqlBuilder.append(sql);
        sqlBuilder.append(" ");
    }

    public void bind(String name, Object value) {
        bindings.put(name, value);
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }
}
