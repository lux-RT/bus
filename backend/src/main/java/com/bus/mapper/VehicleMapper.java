package com.bus.mapper;

import com.bus.dto.VehiclePageQueryDTO;
import com.bus.entity.Vehicle;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Mapper
public interface VehicleMapper {

    /**
     * 分页查询车辆
     * @param vehiclePageQueryDTO
     * @return
     */
    Page<Vehicle> selectAll(VehiclePageQueryDTO vehiclePageQueryDTO);

    Vehicle selectById(Integer id);

    int insert(Vehicle vehicle);

    int update(Vehicle vehicle);

    int deleteById(Integer id);

    List<Vehicle> selectAllList();

    List<Vehicle> selectByLineId(Integer lineId);
}