package com.bus.service;

import com.bus.dto.StationPageQueryDTO;
import com.bus.entity.Station;
import com.bus.result.PageResult;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

public interface StationService {

    /**
     * 站点分页查询
     * @param dto
     * @return
     */
    PageResult page(StationPageQueryDTO dto);

    Station getById(Integer id);

    void add(Station station);

    void update(Station station);

    void deleteById(Integer id);

    List<Station> getAll();
}
