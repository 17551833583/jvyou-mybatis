package com.jvyou.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/3 11:23
 * ---description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private String name;

    private Double price;
}
