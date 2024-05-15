package com.jvyou.mybatis.executor.parameter;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/14 14:50
 * @Description 参数处理器
 */
public interface ParameterHandler {

    /**
     * 预编译SQL语句对象设置参数
     *
     * @param ps         PreparedStatement 预编译语句对象
     * @param paramNames 参数名称列表
     * @param parameter  参数（传递过来的可能是一个 Map 集合）
     */
    void setParameters(PreparedStatement ps, List<String> paramNames, Object parameter);

}
