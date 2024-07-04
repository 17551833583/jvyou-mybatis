package com.jvyou.mybatis.xml.tag;

import com.jvyou.mybatis.xml.DynamicContext;

import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/1 9:44
 * ---description SQL语句XML节点
 */
public interface SqlNode {


    /**
     * 节点处理，每个节点都有自己的处理方式，所以需要实现该方法
     *
     * @param context OGNL上下文环境
     */
    void apply(DynamicContext context);

}
