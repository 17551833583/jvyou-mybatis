package com.jvyou.mybatis.xml.tag;

import com.jvyou.mybatis.xml.DynamicContext;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/1 9:53
 * @Description 混合节点，如何父节点有多个子节点，用于包装子节点
 */
public class MixedSqlNode implements SqlNode{

    private final List<SqlNode> sqlNodes;

    public MixedSqlNode(List<SqlNode> sqlNodes) {
        this.sqlNodes = sqlNodes;
    }

    @Override
    public void apply(DynamicContext context) {
        for (SqlNode sqlNode : sqlNodes) {
            sqlNode.apply(context);
        }
    }
}
