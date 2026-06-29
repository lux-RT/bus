package com.bus.service;

import com.bus.dto.LinePageQueryDTO;
import com.bus.entity.Line;
import com.bus.result.PageResult;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

public interface LineService {

    /**
     * 线路分页查询
     * @param linePageQueryDTO
     * @return
     */
    PageResult page(LinePageQueryDTO linePageQueryDTO);

    Line getById(Integer id);

    void add(Line line);

    void update(Line line);

    void deleteById(Integer id);

    List<Line> getAll();

    List<Line> getByFleetId(Integer fleetId);
}