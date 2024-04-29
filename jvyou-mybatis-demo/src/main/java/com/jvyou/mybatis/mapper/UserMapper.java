package com.jvyou.mybatis.mapper;

import com.jvyou.mybatis.annotations.*;
import com.jvyou.mybatis.entity.User;
import com.jvyou.mybatis.entity.UserVo;

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

    @Select("select * from t_user")
    List<User> getAll();

    @Select("select * from t_user where id=#{id} and name=#{name}")
    User getOne(@Param("name") String name, @Param("id") Integer id);

    @Select("select count(*) from t_user")
    Integer count();

    @Insert("insert into t_user(name,age) values(#{user.name},#{user.age})")
    Integer insertByUser(@Param("user") User user);

    @Insert("insert into t_user(name,age) values(#{name},#{age})")
    Integer insert(@Param("name") String name, @Param("age") Integer age);

    @Update("update t_user set name=#{user.name},age=#{user.age} where id=#{user.id}")
    Integer updateByUser(@Param("user") User user);

    @Update("update t_user set name=#{userVo.user.name},age=#{userVo.user.age} where id=#{userVo.user.id}")
    Integer updateByUserVo(@Param("userVo") UserVo userVo);

    @Update("update t_user set name=#{name},age=#{age} where id=#{id}")
    Integer update(@Param("id") Integer id, @Param("name") String name, @Param("age") Integer age);

    @Delete("delete from t_user where id=#{id}")
    Integer delete(@Param("id") Integer id);

}
