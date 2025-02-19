package com.jvyou.mybatis;


import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/25 19:59
 * ---description 测试原生的 JDBC 接口
 */
public class TestJdbc {

    @Test
    void testJdbc() throws Exception {
        // 1.加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取数据库链接
        String url = "jdbc:mysql://127.0.0.1:3306/jvyou-mybatis?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        // 3.执行数据库操作
        PreparedStatement ps = connection.prepareStatement("select * from t_user where id=?");
        ps.setInt(1, 2);
        ps.execute();
        // 4.获取结果集
        ResultSet resultSet = ps.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + "--" + resultSet.getInt("age"));
        }
        // 5.关闭数据库链接
        resultSet.close();
        ps.close();
        connection.close();
    }

    @Test
    void testAdd() throws ClassNotFoundException, SQLException {
        // 1.加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取数据库链接
        String url = "jdbc:mysql://127.0.0.1:3306/jvyou-mybatis?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        connection.setAutoCommit(false);
        boolean isAuto = connection.getAutoCommit();
        System.out.println("是否自动提交：" + isAuto);
        System.out.println("事务隔离级别：" + connection.getTransactionIsolation());
        // 3.执行数据库操作
        PreparedStatement ps = connection.prepareStatement("insert t_user(name,age) values ('test',10)");
        ps.execute();
        try {
            int i = 1 / 0;
            connection.commit();
            System.out.println(ps.getUpdateCount());
        } catch (Exception e) {
            connection.rollback();
            System.out.println("事务回滚");
        }
        // 5.关闭数据库链接
        ps.close();
        connection.close();
    }

}
