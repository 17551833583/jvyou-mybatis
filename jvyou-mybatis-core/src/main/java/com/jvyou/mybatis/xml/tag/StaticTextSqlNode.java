package com.jvyou.mybatis.xml.tag;

import com.jvyou.mybatis.xml.DynamicContext;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/1 1:13
 * ---description 文本元素
 */
public class StaticTextSqlNode implements SqlNode {

    private final String text;

    public StaticTextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public void apply(DynamicContext context) {
        context.appendSql(text);
    }
}
