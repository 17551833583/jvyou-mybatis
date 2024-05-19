package com.jvyou.mybatis.binding;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.jvyou.mybatis.entity.User;
import com.jvyou.mybatis.entity.UserVo;
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
 * @Date 2024/4/26 13:07
 * @Description
 */
public class MapperProxyInvocationHandlerTest {

    private UserMapper userMapper;

    private SqlSessionFactory sqlSessionFactory;

    private SqlSession session;

    @BeforeEach
    void before() {
        long start = System.currentTimeMillis();
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        this.session = sqlSessionFactory.openSession(true);
        userMapper = session.getMapper(UserMapper.class);
        System.out.println("初始化耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    void getAll() {
        List<User> users = userMapper.getAll();
        System.out.println(JSONUtil.toJsonStr(users));
    }


    @Test
    void testPool() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            System.out.println("第" + (i + 1) + "次获取连接");
            List<User> users = userMapper.getList("jvyou", 1);
            System.out.println(JSONUtil.toJsonStr(users));
            System.out.println("--------------------------");
        }
        System.out.println("执行SQL：" + (System.currentTimeMillis() - start));
    }

    @Test
    void getList() {
        List<User> users = userMapper.getList("jvyou", 1);
        System.out.println(JSONUtil.toJsonStr(users));
    }

    @Test
    void getOne() {
        User jvyou = userMapper.getOne("jvyou", 1);
        System.out.println(JSONUtil.toJsonStr(jvyou));
    }

    @Test
    void count() {
        Integer count = userMapper.count();
        System.out.println(count);
    }

    @Test
    void insertByUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName(RandomUtil.randomString(5));
        user.setAge(RandomUtil.randomInt(0, 100));
        Integer row = userMapper.insertByUser(user);
        try {
            int i = 1 / 0;
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }
        System.out.println(row);
    }

    @Test
    void insert() {
        int row = userMapper.insert(RandomUtil.randomString(5), RandomUtil.randomInt(0, 100));
        System.out.println(row);
    }

    @Test
    void updateByUser() {
        User user = new User();
        user.setName(RandomUtil.randomString(5));
        user.setAge(RandomUtil.randomInt(0, 100));
        user.setId(26005);
        Integer row = userMapper.updateByUser(user);
        System.out.println(row);
    }

    @Test
    void updateByUserVo() {
        User user = new User();
        user.setName(RandomUtil.randomString(5));
        user.setAge(RandomUtil.randomInt(0, 100));
        user.setId(26007);
        Integer row = userMapper.updateByUserVo(new UserVo(user));
        System.out.println(row);
    }

    @Test
    void update() {
        Integer row = userMapper.update(26007, "yya", 18);
        System.out.println(row);
    }

    @Test
    void delete() {
        boolean row = userMapper.delete(26007);
        System.out.println(row);
    }
}
