package com.jvyou.mybatis.parser;

import com.jvyou.mybatis.constant.SymbolKeyword;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/26 14:09
 * ---description Mapper 参数标记处理器,用于处理 #{} 标记
 */
public class ParameterMappingTokenHandler implements TokenHandler, SymbolKeyword {

    private final List<String> params = new ArrayList<>();

    @Override
    public String handleToken(String content) {
        params.add(content);
        return QUESTION_MARK;
    }

    public List<String> getParams() {
        return params;
    }
}
