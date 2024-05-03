package com.jvyou.mybatis.plugin;

import com.jvyou.mybatis.service.UserService;
import com.jvyou.mybatis.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/2 22:15
 * @Description
 */
public class InterceptorChainTest {

    private InterceptorChain chain;

    @BeforeEach
    void setUp() {
        chain = new InterceptorChain();
        chain.addInterceptor(new LimitPlugin());
        chain.addInterceptor(new SqlLogPlugin());
    }

    @Test
    void wrap() {
        UserService userService = chain.wrap(new UserServiceImpl());
        String name = userService.getUserNameById(1);
        System.out.println(name);
    }

}
