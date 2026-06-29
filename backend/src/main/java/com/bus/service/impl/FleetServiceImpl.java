package com.bus.service.impl;

import com.bus.dto.FleetPageQueryDTO;
import com.bus.entity.Fleet;
import com.bus.mapper.FleetMapper;
import com.bus.result.PageResult;
import com.bus.service.FleetService;
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
public class FleetServiceImpl implements FleetService {

    @Autowired
    private FleetMapper fleetMapper;

    @Override
    public PageResult page(FleetPageQueryDTO fleetPageQueryDTO) {
        // 设置默认分页参数
        int pageNum = fleetPageQueryDTO.getPage() != null ? fleetPageQueryDTO.getPage() : 1;
        int pageSize = fleetPageQueryDTO.getPageSize() != null ? fleetPageQueryDTO.getPageSize() : 10;
        PageHelper.startPage(pageNum, pageSize);
        Page<Fleet> page = fleetMapper.selectAll(fleetPageQueryDTO);
        return PageResult.of(page.getTotal(), page.getResult(), pageNum, pageSize);
    }

    @Override
    public Fleet getById(Integer id) {
        return fleetMapper.selectById(id);
    }

    @Override
    public void add(Fleet fleet) {
        fleetMapper.insert(fleet);
    }

    @Override
    public void update(Fleet fleet) {
        fleetMapper.update(fleet);
    }

    @Override
    public void deleteById(Integer id) {
        fleetMapper.deleteById(id);
    }

    @Override
    public List<Fleet> getAll() {
        return fleetMapper.selectAllList();
    }
}