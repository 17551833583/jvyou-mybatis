package com.jvyou.mybatis;

import com.jvyou.mybatis.entity.User;
import com.jvyou.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 16:04
 * @Description
 */
public class TestMybatis {

    @Test
    void test() throws IOException {
        //加载核心配置文件
        InputStream is= Resources.getResourceAsStream("SqlMapConfig.xml");
        //获取SqlSession工厂
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(is);
        //获取SqlSession对象
        SqlSession sqlSession=sessionFactory.openSession();

        sqlSession.select("com.jvyou.mybatis.mapper.UserMapper.getAll", "jvyou", resultContext -> System.out.println(resultContext.getResultObject()));

        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        //执行Sql语句
        List<User> users=mapper.getList("jvyou", 1);
        //打印结果
        for (User user : users) {
            System.out.println(user);
            System.out.println("-------------------------------");
        }
        //释放资源
        sqlSession.close();
    }

}
