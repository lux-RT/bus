package com.bus.service;

import com.bus.dto.VehiclePageQueryDTO;
import com.bus.entity.Vehicle;
import com.bus.result.PageResult;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

public interface VehicleService {

    /**
     * 车辆分页查询
     * @param vehiclePageQueryDTO
     * @return
     */
    PageResult page(VehiclePageQueryDTO vehiclePageQueryDTO);

    Vehicle getById(Integer id);

    void add(Vehicle vehicle);

    void update(Vehicle vehicle);

    void deleteById(Integer id);

    List<Vehicle> getAll();

    List<Vehicle> getByLineId(Integer lineId);
}