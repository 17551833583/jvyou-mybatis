package com.jvyou.mybatis;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/28 17:21
 * @Description 测试JDBC批量存储
 */
public class TestBatchSave {

    @Test
    public void testBatchSave() throws Exception {
        long start = System.currentTimeMillis();
        // 1.加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取数据库链接
        String url = "jdbc:mysql://127.0.0.1:3306/jvyou-mybatis?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        // 3.执行数据库操作
        PreparedStatement ps = connection.prepareStatement("insert into t_user(name, age) VALUES (?,?)");
        for (int i = 0; i < 1000; i++) {
            ps.setString(1, "name" + i);
            ps.setInt(2, i);
            ps.execute();

        }
        ps.close();
        connection.close();
        // 耗时：7215
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    public void testBatchSave2() throws Exception {
        long start = System.currentTimeMillis();
        // 1.加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取数据库链接
        String url = "jdbc:mysql://127.0.0.1:3306/jvyou-mybatis?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        // 3.执行数据库操作
        PreparedStatement ps = connection.prepareStatement("insert into t_user(name, age) VALUES (?,?)");
        for (int i = 0; i < 1000; i++) {
            ps.setString(1, "name" + i);
            ps.setInt(2, i);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
        connection.close();
        // 耗时：917
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

}
