package com.bus.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

/**
 * 驾驶员实体类，对应 driver 表
 */
@Data
public class Driver {

    /** 驾驶员ID */
    private Integer driverId;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String gender;

    /** 年龄 */
    private Integer age;

    /** 联系电话 */
    private String phone;

    /** 所属线路ID */
    private Integer lineId;

    /** 所属线路名称（关联查询） */
    private String lineName;

    /** 所属车队名称（关联查询） */
    private String fleetName;

    /** 是否路队长 */
    private Boolean isRoadCaptain;

    /** 是否车队长 */
    private Boolean isCaptain;

    /** 入职日期 */
    private LocalDate hireDate;
}