package com.jvyou.mybatis.mapper;

import com.jvyou.mybatis.annotations.Param;
import com.jvyou.mybatis.entity.Order;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/3 11:23
 * ---description
 */
public interface OrderMapper {

    List<Order> getList();


    Order getById(@Param("id") Integer id);

}
