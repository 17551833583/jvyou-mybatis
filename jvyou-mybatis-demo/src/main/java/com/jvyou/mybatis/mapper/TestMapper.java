package com.jvyou.mybatis.mapper;

import com.jvyou.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 20:12
 * @Description
 */
public interface TestMapper {


    @Select({"select * from t_user where id=#{id} and name=#{name}"})
    User getOne(@Param("name") String name, @Param("id") Integer id);

}
