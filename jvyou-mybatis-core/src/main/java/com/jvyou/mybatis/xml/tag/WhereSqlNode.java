package com.jvyou.mybatis.xml.tag;

import com.jvyou.mybatis.xml.DynamicContext;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/1 1:12
 * ---description where标签
 */
public class WhereSqlNode implements SqlNode {

    private final SqlNode sqlNode;

    public WhereSqlNode(SqlNode sqlNode) {
        this.sqlNode = sqlNode;
    }

    @Override
    public void apply(DynamicContext context) {
        context.appendSql("where ");
        sqlNode.apply(context);
    }
}
