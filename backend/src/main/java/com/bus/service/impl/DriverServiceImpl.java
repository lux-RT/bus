package com.bus.service.impl;

import com.bus.dto.DriverPageQueryDTO;
import com.bus.entity.Driver;
import com.bus.mapper.DriverMapper;
import com.bus.result.PageResult;
import com.bus.service.DriverService;
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
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverMapper driverMapper;

    @Override
    public PageResult page(DriverPageQueryDTO driverPageQueryDTO) {
        // 设置默认分页参数
        int pageNum = driverPageQueryDTO.getPage() != null ? driverPageQueryDTO.getPage() : 1;
        int pageSize = driverPageQueryDTO.getPageSize() != null ? driverPageQueryDTO.getPageSize() : 10;
        PageHelper.startPage(pageNum, pageSize);
        Page<Driver> page = driverMapper.selectAll(driverPageQueryDTO);
        return PageResult.of(page.getTotal(), page.getResult(), pageNum, pageSize);
    }

    @Override
    public Driver getById(Integer id) {
        return driverMapper.selectById(id);
    }

    @Override
    public void add(Driver driver) {
        driverMapper.insert(driver);
    }

    @Override
    public void update(Driver driver) {
        driverMapper.update(driver);
    }

    @Override
    public void deleteById(Integer id) {
        driverMapper.deleteById(id);
    }

    @Override
    public List<Driver> getAll() {
        return driverMapper.selectAllList();
    }

    @Override
    public List<Driver> getByLineId(Integer lineId) {
        return driverMapper.selectByLineId(lineId);
    }

    @Override
    public List<Driver> getByFleetId(Integer fleetId) {
        return driverMapper.selectByFleetId(fleetId);
    }
}