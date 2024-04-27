package com.jvyou.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 16:45
 * @Description Integer 类型参数处理器
 */
public class IntegerParamHandler implements ParamTypeHandler<Integer> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Integer value) throws SQLException {
        ps.setInt(i, value);
    }
}
