package com.bus.entity;

import lombok.Data;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

/**
 * 违章类型实体类，对应 violation_type 表
 */
@Data
public class ViolationType {

    /** 类型ID */
    private Integer typeId;

    /** 类型名称 */
    private String typeName;
}