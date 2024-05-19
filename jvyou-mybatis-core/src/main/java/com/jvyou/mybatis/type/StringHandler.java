package com.jvyou.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 16:44
 * @Description String 类型的参数处理器
 */
public class StringHandler implements TypeHandler<String> {
    @Override
    public void setParameter(PreparedStatement ps, int i, String value) throws SQLException {
        ps.setString(i, value);
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }
}
