package com.jvyou.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/25 20:09
 * ---description 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    private String name;

    private Integer age;
}
