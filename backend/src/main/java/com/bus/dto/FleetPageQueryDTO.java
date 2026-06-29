package com.bus.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */
@Data
public class FleetPageQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页记录数 */
    private Integer pageSize;

    /** 车队名称（模糊查询） */
    private String fleetName;

    /** 成立时间开始 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    /** 成立时间结束 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
}