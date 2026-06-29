package com.bus.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

/**
 * 车辆实体类，对应 vehicle 表
 */
@Data
public class Vehicle {

    /** 车辆ID */
    private Integer vehicleId;

    /** 车牌号 */
    private String licensePlate;

    /** 所属线路ID */
    private Integer lineId;

    /** 车型 */
    private String model;

    /** 购买时间 */
    private LocalDate purchaseTime;

    /** 车辆状态 */
    private String status;

    /** 所属线路名称（关联查询，非数据库字段） */
    private String lineName;
}