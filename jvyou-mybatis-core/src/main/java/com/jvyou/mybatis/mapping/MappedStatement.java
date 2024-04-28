package com.jvyou.mybatis.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/27 20:12
 * @Description Mapper 配置信息
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MappedStatement {

    // 唯一标识 eg: com.jvyou.mybatis.mapper.UserMapper.getAll
    private String id;
    // sql
    private String sql;
    // 返回类型
    private Class<?> resultType;
    // sql 类型
    private SqlCommandType sqlCommandType;

}
