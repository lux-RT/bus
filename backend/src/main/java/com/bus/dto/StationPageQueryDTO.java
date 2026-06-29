package com.bus.dto;

import lombok.Data;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */
@Data
public class StationPageQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页记录数 */
    private Integer pageSize;

    /** 站点名称或地址（模糊查询） */
    private String keyword;
}
