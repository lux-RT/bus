package com.bus.dto;

import lombok.Data;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */
@Data
public class DriverPageQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页记录数 */
    private Integer pageSize;

    /** 姓名或工号（模糊查询） */
    private String keyword;

    /** 线路ID */
    private Integer lineId;

    /** 车队ID */
    private Integer fleetId;

    /** 性别 */
    private String gender;
}
