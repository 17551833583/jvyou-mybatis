package com.jvyou.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/26 16:42
 * ---description 参数类型处理器
 */
public interface TypeHandler<T> {

    void setParameter(PreparedStatement ps, int i, T value) throws SQLException;

    T getResult(ResultSet rs, String columnName) throws SQLException;

}
