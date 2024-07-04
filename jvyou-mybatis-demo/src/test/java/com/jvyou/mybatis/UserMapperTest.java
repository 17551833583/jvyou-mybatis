package com.jvyou.mybatis;

import cn.hutool.json.JSONUtil;
import com.jvyou.mybatis.entity.User;
import com.jvyou.mybatis.mapper.UserMapper;
import com.jvyou.mybatis.session.SqlSession;
import com.jvyou.mybatis.session.SqlSessionFactory;
import com.jvyou.mybatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/3 15:04
 * @Description
 */
public class UserMapperTest {

    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    void before() {
        long start = System.currentTimeMillis();
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        System.out.println("初始化耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    void getAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.getAll();
        System.out.println(JSONUtil.toJsonStr(users));
    }

}
