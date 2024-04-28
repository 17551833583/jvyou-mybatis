package com.jvyou.mybatis.binding;

import cn.hutool.json.JSONUtil;
import com.jvyou.mybatis.builder.XMLConfigBuilder;
import com.jvyou.mybatis.entity.User;
import com.jvyou.mybatis.mapper.UserMapper;
import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.session.SqlSession;
import com.jvyou.mybatis.session.SqlSessionFactory;
import com.jvyou.mybatis.session.SqlSessionFactoryBuilder;
import com.jvyou.mybatis.session.defaults.DefaultSqlSeiionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 13:07
 * @Description
 */
public class MapperProxyInvocationHandlerTest {

    private UserMapper userMapper;

    @BeforeEach
    void before() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession session = sqlSessionFactory.openSession();
        userMapper = session.getMapper(UserMapper.class);
    }

    @Test
    void test() {
        List<User> users = userMapper.getAll("jvyou", 1);
        System.out.println(JSONUtil.toJsonStr(users));
    }

    @Test
    void test2() {
        userMapper.getOne("jvyou", 1);
    }

    @Test
    void test3() {
        userMapper.count();
    }

}
