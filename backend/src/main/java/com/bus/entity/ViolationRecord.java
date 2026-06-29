package com.bus.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

/**
 * 违章记录实体类，对应 violation_record 表
 */
@Data
public class ViolationRecord {

    /** 记录ID */
    private Integer recordId;

    /** 驾驶员ID */
    private Integer driverId;

    /** 车辆ID */
    private Integer vehicleId;

    /** 线路ID */
    private Integer lineId;

    /** 站点ID */
    private Integer stationId;

    /** 违章类型ID */
    private Integer violationTypeId;

    /** 违章时间 */
    private LocalDateTime violationTime;

    /** 违章描述 */
    private String description;

    /** 录入人ID */
    private Integer inputterId;

    // ===== 关联查询字段（非数据库字段）=====

    /** 司机姓名 */
    private String driverName;

    /** 车牌号 */
    private String licensePlate;

    /** 线路名称 */
    private String lineName;

    /** 违章类型名称 */
    private String typeName;

    /** 站点名称 */
    private String stationName;
}