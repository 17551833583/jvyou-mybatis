package com.jvyou.mybatis.service;

import com.google.common.collect.Lists;
import com.jvyou.mybatis.entity.User;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/30 14:44
 * ---description
 */
public class UserServiceImpl implements UserService {

    public List<User> getAll() {
        System.out.println("getAll");
        return Lists.newArrayList(new User(1, "jvyou", 18), new User(2, "jvyou2", 20));
    }

    @Override
    public String getUserNameById(Integer id) {
        System.out.println("getUserNameById");
        return id.toString();
    }
}
