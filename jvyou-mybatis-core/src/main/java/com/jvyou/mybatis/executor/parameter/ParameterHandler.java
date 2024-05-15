package com.jvyou.mybatis.executor.parameter;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/14 14:50
 * @Description 参数处理器
 */
public interface ParameterHandler {

    void setParameters(PreparedStatement ps,List<String> paramNames, Object parameter);

}
