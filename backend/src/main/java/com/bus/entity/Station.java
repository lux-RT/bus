package com.bus.entity;

import lombok.Data;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

/**
 * 站点实体类，对应 station 表
 */
@Data
public class Station {

    /** 站点ID */
    private Integer stationId;

    /** 站点名称 */
    private String stationName;

    /** 站点地址 */
    private String address;
}