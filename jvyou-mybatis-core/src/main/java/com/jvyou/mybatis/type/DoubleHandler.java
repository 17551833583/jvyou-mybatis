package com.jvyou.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/3 15:30
 * @Description Double类型处理器
 */
public class DoubleHandler implements TypeHandler<Double> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Double value) throws SQLException {
        ps.setDouble(i, value);
    }

    @Override
    public Double getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getDouble(columnName);
    }
}
