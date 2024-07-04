package com.jvyou.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/3 15:30
 * ---description Long类型处理器
 */
public class LongHandler implements TypeHandler<Long> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Long value) throws SQLException {
        ps.setLong(i, value);
    }

    @Override
    public Long getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getLong(columnName);
    }
}
