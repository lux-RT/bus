package com.bus.service.impl;

import com.bus.dto.VehiclePageQueryDTO;
import com.bus.entity.Vehicle;
import com.bus.mapper.VehicleMapper;
import com.bus.result.PageResult;
import com.bus.service.VehicleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public PageResult page(VehiclePageQueryDTO vehiclePageQueryDTO) {
        // 设置默认分页参数
        int pageNum = vehiclePageQueryDTO.getPage() != null ? vehiclePageQueryDTO.getPage() : 1;
        int pageSize = vehiclePageQueryDTO.getPageSize() != null ? vehiclePageQueryDTO.getPageSize() : 10;
        PageHelper.startPage(pageNum, pageSize);
        Page<Vehicle> page = vehicleMapper.selectAll(vehiclePageQueryDTO);
        return PageResult.of(page.getTotal(), page.getResult(), pageNum, pageSize);
    }

    @Override
    public Vehicle getById(Integer id) {
        return vehicleMapper.selectById(id);
    }

    @Override
    public void add(Vehicle vehicle) {
        vehicleMapper.insert(vehicle);
    }

    @Override
    public void update(Vehicle vehicle) {
        vehicleMapper.update(vehicle);
    }

    @Override
    public void deleteById(Integer id) {
        vehicleMapper.deleteById(id);
    }

    @Override
    public List<Vehicle> getAll() {
        return vehicleMapper.selectAllList();
    }

    @Override
    public List<Vehicle> getByLineId(Integer lineId) {
        return vehicleMapper.selectByLineId(lineId);
    }
}