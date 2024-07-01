package com.jvyou.mybatis.xml.tag;

import lombok.SneakyThrows;
import ognl.Ognl;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/1 1:12
 * @Description if标签
 */
public class IfSqlNode implements SqlNode {

    /**
     * if语句的判断条件
     */
    private String test;

    /**
     * if语句的子节点
     */
    private SqlNode sqlNode;

    public IfSqlNode(String test, SqlNode sqlNode) {
        this.test = test;
        this.sqlNode = sqlNode;
    }

    @SneakyThrows
    @Override
    public void apply(Object context) {
        // test 条件是否成立
        Boolean value = (Boolean) Ognl.getValue(test, context);
        if (value) {
            sqlNode.apply(context);
        }
    }
}
