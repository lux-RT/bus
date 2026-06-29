package com.bus.dto;

import lombok.Data;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */
@Data
public class VehiclePageQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页记录数 */
    private Integer pageSize;

    /** 车牌号或车型号（模糊查询） */
    private String keyword;

    /** 线路ID */
    private Integer lineId;

    /** 车辆状态 */
    private String status;
}