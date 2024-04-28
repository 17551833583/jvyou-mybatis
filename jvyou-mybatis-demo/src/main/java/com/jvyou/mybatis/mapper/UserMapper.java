package com.jvyou.mybatis.mapper;

import com.jvyou.mybatis.annotations.*;
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

    @Select("select * from t_user where id=#{id} and name=#{name}")
    User getOne(@Param("name") String name, @Param("id") Integer id);

    @Select("select count(*) from t_user")
    Integer count();

    @Insert("insert into t_user(name,age) values(#{user.name},#{user.age})")
    Integer insert(@Param("user") User user);

    @Update("update t_user set name=#{user.name},age=#{user.age} where id=#{user.id}")
    Integer update(@Param("user") User user);

    @Delete("delete from t_user where id=#{id}")
    Integer delete(@Param("id") Integer id);

}
