package com.bus.mapper;

import com.bus.dto.DriverPageQueryDTO;
import com.bus.entity.Driver;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Mapper
public interface DriverMapper {

    List<Driver> selectList();

    Driver selectById(Integer id);

    int insert(Driver driver);

    int update(Driver driver);

    int deleteById(Integer id);

    /**
     * 分页查询司机
     * @param driverPageQueryDTO
     * @return
     */
    Page<Driver> selectAll(DriverPageQueryDTO driverPageQueryDTO);

    List<Driver> selectAllList();

    List<Driver> selectByLineId(Integer lineId);

    List<Driver> selectByFleetId(Integer fleetId);
}