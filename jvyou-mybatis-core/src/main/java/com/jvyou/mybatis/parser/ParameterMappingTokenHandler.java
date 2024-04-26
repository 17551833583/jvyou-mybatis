package com.jvyou.mybatis.parser;

import com.jvyou.mybatis.constant.Keyword;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 14:09
 * @Description Mapper 参数标记处理器
 */
public class ParameterMappingTokenHandler implements TokenHandler, Keyword {

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
