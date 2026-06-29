package com.bus.dto;

import lombok.Data;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */
@Data
public class LinePageQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页记录数 */
    private Integer pageSize;

    /** 线路名称（模糊查询） */
    private String lineName;

    /** 车队ID */
    private Integer fleetId;
}