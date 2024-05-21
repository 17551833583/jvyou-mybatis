package com.jvyou.mybatis.cache;

import com.jvyou.mybatis.mapper.UserMapper;
import com.jvyou.mybatis.session.SqlSession;
import com.jvyou.mybatis.session.SqlSessionFactory;
import com.jvyou.mybatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/21 14:22
 * @Description
 */
public class PerpetualCacheTest {

    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    void before() {
        long start = System.currentTimeMillis();
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        System.out.println("初始化耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    void testUpdate() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(userMapper.getList("jvyou", 1));
        System.out.println(userMapper.getList("jvyou", 1));
        userMapper.update(1, "jvyou", 22);
        System.out.println(userMapper.getList("jvyou", 1));
    }

    @Test
    void testLocalCache() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(userMapper.getList("jvyou", 1));
        System.out.println(userMapper.getList("jvyou", 1));
        // 新的会话
        SqlSession sqlSession1 = sqlSessionFactory.openSession(false);
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        System.out.println(userMapper1.getList("jvyou", 1));
    }


}
