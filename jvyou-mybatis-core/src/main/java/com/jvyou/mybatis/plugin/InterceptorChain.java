package com.jvyou.mybatis.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/2 22:13
 * @Description 插件拦截器责任链
 */
public class InterceptorChain {

    private final List<PluginInterceptor> interceptors = new ArrayList<>();

    public void addInterceptor(PluginInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public <T> T wrap(T target) {
        for (PluginInterceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    /**
     * 获取拦截器列表
     *
     * @return 不可修改的拦截器列表
     */
    public List<PluginInterceptor> getInterceptors() {
        return Collections.unmodifiableList(interceptors);
    }

}
