package com.bus.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

/**
 * 车队实体类，对应 fleet 表
 */
@Data
public class Fleet {

    /** 车队ID */
    private Integer fleetId;

    /** 车队名称 */
    private String fleetName;

    /** 车队长ID */
    private Integer captainId;

    /** 创建时间 */
    private LocalDate createTime;

    /** 队长姓名（关联查询，非数据库字段） */
    private String captainName;
}