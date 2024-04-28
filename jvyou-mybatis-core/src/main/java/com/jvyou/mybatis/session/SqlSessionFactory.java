package com.jvyou.mybatis.session;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 21:35
 * @Description SqlSession 工厂类
 */
public interface SqlSessionFactory {

    SqlSession openSession();

}
