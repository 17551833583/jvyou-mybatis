package com.jvyou.mybatis.proxy;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 21:49
 * @Description 夫妻类
 */
public class Spouse implements Marry {
    @Override
    public void marry() {
        System.out.println("我们要结婚啦");
    }
}
