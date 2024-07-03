package com.jvyou.mybatis.xml.tag;

import com.jvyou.mybatis.parser.BindingTokenHandler;
import com.jvyou.mybatis.parser.GenericTokenParser;
import com.jvyou.mybatis.xml.DynamicContext;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/1 1:13
 * @Description 文本元素, 需要解析 ${}
 */
public class TextSqlNode implements SqlNode {

    private final String text;

    public TextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public void apply(DynamicContext context) {
        BindingTokenHandler bindingTokenHandler = new BindingTokenHandler(context.getBindings());
        GenericTokenParser parser = new GenericTokenParser("${", "}", bindingTokenHandler);
        String sql = parser.parse(text);
        context.appendSql(sql);
    }
}
