package com.jvyou.mybatis.session;

import com.jvyou.mybatis.builder.XMLConfigBuilder;
import com.jvyou.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 21:44
 * @Description SqlSession 工厂构建者
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build() {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse();
        return new DefaultSqlSessionFactory(configuration);
    }

    public SqlSessionFactory build(InputStream inputStream) {
        return build();
    }

    public SqlSessionFactory build(InputStream inputStream, String environment) {
        return build();
    }

    public SqlSessionFactory build(InputStream inputStream, Properties properties) {
        return build();
    }

    public SqlSessionFactory build(InputStream inputStream, String env, Properties props) {
        return build();
    }

    public SqlSessionFactory build(Configuration config) {
        return build();
    }
}
