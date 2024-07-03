package com.jvyou.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/3 11:23
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private String name;

    private Double price;
}
