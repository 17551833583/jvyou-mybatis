package com.jvyou.mybatis.mapper;

import com.jvyou.mybatis.entity.Order;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/3 11:23
 * @Description
 */
public interface OrderMapper {

    List<Order> getList();

}
