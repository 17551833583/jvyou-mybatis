package com.jvyou.mybatis.builder;

import com.jvyou.mybatis.session.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/27 22:18
 * ---description
 */
class XMLConfigBuilderTest {

    @Test
    void parse() {

        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse();

    }
}