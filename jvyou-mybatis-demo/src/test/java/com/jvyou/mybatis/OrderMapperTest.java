package com.jvyou.mybatis;

import com.jvyou.mybatis.builder.XMLConfigBuilder;
import com.jvyou.mybatis.entity.Order;
import com.jvyou.mybatis.mapper.OrderMapper;
import com.jvyou.mybatis.session.SqlSession;
import com.jvyou.mybatis.session.SqlSessionFactory;
import com.jvyou.mybatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/3 15:04
 * @Description
 */
public class OrderMapperTest {

    @Test
    void testList() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession session = sqlSessionFactory.openSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);
        List<Order> orders = orderMapper.getList();
        System.out.println(orders);
    }

}
