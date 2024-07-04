package com.jvyou.mybatis.parser;

import lombok.SneakyThrows;
import ognl.Ognl;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/4 0:20
 * ---description 用于处理动态SQL的 ${} 标记
 */
public class BindingTokenHandler implements TokenHandler {

    // Ognl 解析参数的上下文
    private final Object context;

    public BindingTokenHandler(Object context) {
        this.context = context;
    }

    /**
     * 处理 ${} 标记,将${}里面的表达式通过ognl解析成对应的值
     *
     * @param content ${} 标记里面的表达式
     * @return ognl解析之后的值
     */
    @SneakyThrows
    @Override
    public String handleToken(String content) {
        return String.valueOf(Ognl.getValue(content, context));
    }
}
