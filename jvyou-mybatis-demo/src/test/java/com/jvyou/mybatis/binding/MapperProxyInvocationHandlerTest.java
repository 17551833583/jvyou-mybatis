package com.jvyou.mybatis.binding;

import cn.hutool.json.JSONUtil;
import com.jvyou.mybatis.builder.XMLConfigBuilder;
import com.jvyou.mybatis.entity.User;
import com.jvyou.mybatis.mapper.UserMapper;
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
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        userMapper = new MapperProxyFactory().getProxy(UserMapper.class, xmlConfigBuilder.parse());
    }

    @Test
    void test() {
        long start = System.currentTimeMillis();
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        userMapper = new MapperProxyFactory().getProxy(UserMapper.class, xmlConfigBuilder.parse());
        List<User> users = userMapper.getAll("jvyou", 1);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
        List<User> users2 = userMapper.getAll("jvyou", 1);
        System.out.println("耗时：" + (System.currentTimeMillis() - end));
        users.forEach((user) -> {
            System.out.println(JSONUtil.parse(user));
        });
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
