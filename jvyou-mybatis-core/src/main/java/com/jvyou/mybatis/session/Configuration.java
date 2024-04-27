package com.jvyou.mybatis.session;

import com.jvyou.mybatis.mapping.MappedStatement;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/27 20:03
 * @Description 核心配置类
 */
@Data
public class Configuration {

    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    /**
     * 添加一个映射语句到映射语句集合中。
     *
     * @param mappedStatement 映射语句对象，包含了SQL语句及其相关配置信息。
     */
    public void addMappedStatement(MappedStatement mappedStatement) {
        // 将映射语句对象添加到映射语句集合中，使用ID作为键
        mappedStatements.put(mappedStatement.getId(), mappedStatement);
    }

    public MappedStatement getMappedStatement(String id) {
        // 根据ID从映射语句集合中获取映射语句对象
        return mappedStatements.get(id);
    }

}
