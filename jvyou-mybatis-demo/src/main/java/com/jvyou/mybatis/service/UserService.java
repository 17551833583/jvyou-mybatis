package com.jvyou.mybatis.service;

import com.jvyou.mybatis.entity.User;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/30 14:44
 * ---description
 */
public interface UserService {

    List<User> getAll();

    String getUserNameById(Integer id);

}
