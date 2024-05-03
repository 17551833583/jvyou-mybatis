package com.jvyou.mybatis.plugin;

import com.google.common.collect.Lists;
import com.jvyou.mybatis.service.UserService;
import com.jvyou.mybatis.service.UserServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 14:42
 * @Description
 */
class PluginTest2 {

    @SneakyThrows
    @Test
    void wrap() {

        ArrayList<PluginInterceptor> pluginInterceptors = Lists.newArrayList(
                new LimitPlugin(),
                new SqlLogPlugin()

        );
        UserService userService = new UserServiceImpl();

        for (PluginInterceptor interceptor : pluginInterceptors) {
            userService = interceptor.plugin(userService);
        }
        String name = userService.getUserNameById(1);
        System.out.println(name);
    }
}