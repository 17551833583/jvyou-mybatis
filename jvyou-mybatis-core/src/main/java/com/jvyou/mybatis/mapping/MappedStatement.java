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

    /**
     * 唯一标识 eg: com.jvyou.mybatis.mapper.UserMapper.getAll
     * 由类名和方法名组成
     */
    private String id;
    /**
     * 原始的 SQL语句
     */
    private String sql;
    /**
     * 返回类型
     */
    private Class<?> resultType;
    /**
     * SQL 命令类型
     */
    private SqlCommandType sqlCommandType;
    /**
     * 是否是查询多条记录
     */
    private boolean isSelectMany;

}
