package com.jvyou.mybatis.mapper;

import com.jvyou.mybatis.annotations.Param;
import com.jvyou.mybatis.annotations.Select;
import com.jvyou.mybatis.entity.User;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 20:12
 * @Description
 */
public interface UserMapper {

    @Select("select * from t_user where id=#{id} and name=#{name}")
    List<User> getAll(@Param("name") String name, @Param("id") Integer id);

}
