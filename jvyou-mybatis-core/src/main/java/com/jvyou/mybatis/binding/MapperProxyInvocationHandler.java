package com.jvyou.mybatis.binding;

import com.jvyou.mybatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.*;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 23:36
 * @Description Mapper 代理增强
 */
public class MapperProxyInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Select selectAnnotation = method.getAnnotation(Select.class);
        String sql = selectAnnotation.value();

        Connection connection = getConnection();
        // 3.执行数据库操作
        PreparedStatement ps = connection.prepareStatement(sql);
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

        return null;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库链接
        String url = "jdbc:mysql://127.0.0.1:3306/jvyou-mybatis?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        String username = "root";
        String password = "123456";
        return DriverManager.getConnection(url, username, password);
    }
}
