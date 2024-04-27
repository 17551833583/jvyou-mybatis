package com.jvyou.mybatis.binding;

import cn.hutool.json.JSONUtil;
import com.jvyou.mybatis.binding.MapperProxyFactory;
import com.jvyou.mybatis.entity.User;
import com.jvyou.mybatis.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 13:07
 * @Description
 */
public class MapperProxyInvocationHandlerTest {

    @Test
    void test() {
        UserMapper proxy = new MapperProxyFactory().getProxy(UserMapper.class);
        List<User> users = proxy.getAll("jvyou", 1);
        users.forEach((user) -> {
            System.out.println(JSONUtil.parse(user));
        });
    }

    @Test
    void test2() {
        UserMapper proxy = new MapperProxyFactory().getProxy(UserMapper.class);
        proxy.getOne("jvyou", 1);
    }

    @Test
    void test3() {
        UserMapper proxy = new MapperProxyFactory().getProxy(UserMapper.class);
        proxy.count();
    }

}
