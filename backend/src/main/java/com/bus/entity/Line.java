package com.bus.entity;

import lombok.Data;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

/**
 * 线路实体类，对应 line 表
 */
@Data
public class Line {

    /** 线路ID */
    private Integer lineId;

    /** 线路名称 */
    private String lineName;

    /** 所属车队ID */
    private Integer fleetId;

    /** 总站点数 */
    private Integer totalStations;

    /** 路队长姓名（关联查询，非数据库字段） */
    private String roadCaptainName;

    /** 所属车队名称（关联查询，非数据库字段） */
    private String fleetName;
}