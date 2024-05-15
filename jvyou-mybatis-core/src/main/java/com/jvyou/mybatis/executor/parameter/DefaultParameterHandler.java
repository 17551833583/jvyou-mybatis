package com.jvyou.mybatis.executor.parameter;

import com.jvyou.mybatis.exception.JvyouMybatisException;
import com.jvyou.mybatis.session.Configuration;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/14 18:55
 * @Description
 */
public class DefaultParameterHandler implements ParameterHandler {

    private Configuration configuration;

    public DefaultParameterHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void setParameters(PreparedStatement ps, List<String> paramNames, Object parameter) {
        // Mapper 代理方法传递过来的真实参数，key值为 Param 注解 value 的值
        Map<String, Object> paramValueMap = (Map<String, Object>) parameter;
        if (paramValueMap != null && paramNames.size() > 0) {
            for (int i = 0; i < paramNames.size(); i++) {
                String paramName = paramNames.get(i);
                Object value;
                // 如果由 “.” 号，说明是 Mapper 方法传递过来的对象
                if (paramName.contains(".")) {
                    String[] names = paramName.split("\\.");
                    value = paramValueMap.get(names[0]);
                    for (int j = 1; j < names.length; j++) {
                        value = getFieldValue(names[j], value.getClass(), value);
                    }
                } else {
                    value = paramValueMap.get(paramName);
                }
                try {
                    configuration.getParamTypeHandler(value.getClass()).setParameter(ps, i + 1, value);
                } catch (SQLException e) {
                    throw new JvyouMybatisException("Populating the value passed by the method to PreparedStatement error, nested exception is:\n" + e);
                }
            }
        }
    }

    private Object getFieldValue(String fieldName, Class<?> clazz, Object obj) {
        if (obj == null || obj instanceof Class) {
            return null;
        }
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
