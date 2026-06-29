package com.bus.entity;

import lombok.Data;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

/**
 * 线路-站点关联实体类，对应 line_station 表
 */
@Data
public class LineStation {

    /** 线路ID */
    private Integer lineId;

    /** 站点ID */
    private Integer stationId;

    /** 站点序号 */
    private Integer sequence;
}