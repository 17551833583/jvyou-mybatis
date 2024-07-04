package com.jvyou.mybatis.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/1 1:15
 * ---description 动态上下文
 */
public class DynamicContext {

    private final StringBuilder sqlBuilder = new StringBuilder();

    private final Map<String, Object> bindings ;

    public DynamicContext(Map<String, Object> bindings) {
        this.bindings = bindings;
    }

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
