package com.jvyou.mybatis.plugin;

import com.google.common.collect.Lists;
import com.jvyou.mybatis.service.UserService;
import com.jvyou.mybatis.service.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/30 14:42
 * @Description
 */
class PluginTest {

    @Test
    void wrap() {
        ArrayList<PluginInterceptor> pluginInterceptors = Lists.newArrayList(
                new SqlLogPlugin(),
                new LimitPlugin()
        );
        UserService userService = new UserServiceImpl();
        for (PluginInterceptor pluginInterceptor : pluginInterceptors) {
            userService = Plugin.wrap(userService, pluginInterceptor);
        }
        String name = userService.getUserNameById(1001);
        System.out.println(name);
    }
}