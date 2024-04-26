package com.jvyou.mybatis.mapper.binding;

import com.jvyou.mybatis.binding.MapperProxyFactory;
import com.jvyou.mybatis.mapper.UserMapper;
import org.junit.jupiter.api.Test;

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
        proxy.getAll();
    }

}
