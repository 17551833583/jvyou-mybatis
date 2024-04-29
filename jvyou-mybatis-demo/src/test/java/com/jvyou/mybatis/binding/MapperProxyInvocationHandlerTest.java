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

    @BeforeEach
    void before() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession session = sqlSessionFactory.openSession();
        userMapper = session.getMapper(UserMapper.class);
    }

    @Test
    void getAll() {
        List<User> users = userMapper.getAll();
        System.out.println(JSONUtil.toJsonStr(users));
    }

    @Test
    void getAll2() {
        List<User> users = userMapper.getAll("jvyou", 1);
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
        User user = new User();
        user.setName(RandomUtil.randomString(5));
        user.setAge(RandomUtil.randomInt(0, 100));
        Integer row = userMapper.insertByUser(user);
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
        user.setId(26005);
        Integer row = userMapper.updateByUserVo(new UserVo(user));
        System.out.println(row);
    }

    @Test
    void update() {
        Integer row = userMapper.update(26005, "yya", 18);
        System.out.println(row);
    }

    @Test
    void delete() {
        boolean row = userMapper.delete(26006);
        System.out.println(row);
    }
}
