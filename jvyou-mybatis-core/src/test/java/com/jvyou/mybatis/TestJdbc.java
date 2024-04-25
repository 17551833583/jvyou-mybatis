package com.jvyou.mybatis;



import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 19:59
 * @Description 测试原生的 JDBC 接口
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

}
