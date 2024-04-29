package com.jvyou.mybatis.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/29 19:20
 * @Description 解析后的 SQL 以及参数名称 列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoundSql {

    private String parsedSql;

    private List<String> paramNames;

}
