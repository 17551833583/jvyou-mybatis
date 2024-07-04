package com.jvyou.mybatis.parser;

import org.junit.jupiter.api.Test;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/26 13:36
 * ---description
 */
class GenericTokenParserTest {

    @Test
    void parse() {
        String text = "select * from t_user where id=#{id} and name=#{name}";
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}",tokenHandler);
        System.out.println(genericTokenParser.parse(text));

        System.out.println(tokenHandler.getParams());
    }
}