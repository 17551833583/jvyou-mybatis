package com.jvyou.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/26 16:45
 * ---description Integer 类型参数处理器
 */
public class IntegerHandler implements TypeHandler<Integer> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Integer value) throws SQLException {
        ps.setInt(i, value);
    }

    @Override
    public Integer getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getInt(columnName);
    }
}
