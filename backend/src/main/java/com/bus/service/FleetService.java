package com.bus.service;

import com.bus.dto.FleetPageQueryDTO;
import com.bus.entity.Fleet;
import com.bus.result.PageResult;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */
public interface FleetService {

    /**
     * 车队分页查询
     * @param fleetPageQueryDTO
     * @return
     */
    PageResult page(FleetPageQueryDTO fleetPageQueryDTO);

    Fleet getById(Integer id);

    void add(Fleet fleet);

    void update(Fleet fleet);

    void deleteById(Integer id);

    List<Fleet> getAll();
}