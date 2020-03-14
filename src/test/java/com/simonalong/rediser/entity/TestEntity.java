package com.simonalong.rediser.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shizi
 * @since 2020/3/14 下午1:22
 */
@Data
public class TestEntity implements Serializable {

    private String name;
    private Integer age;
}
