package com.bus.service;

import com.bus.dto.DriverPageQueryDTO;
import com.bus.entity.Driver;
import com.bus.result.PageResult;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

public interface DriverService {

    /**
     * 司机分页查询
     * @param driverPageQueryDTO
     * @return
     */
    PageResult page(DriverPageQueryDTO driverPageQueryDTO);

    Driver getById(Integer id);

    void add(Driver driver);

    void update(Driver driver);

    void deleteById(Integer id);

    List<Driver> getAll();

    List<Driver> getByLineId(Integer lineId);

    List<Driver> getByFleetId(Integer fleetId);
}