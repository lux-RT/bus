package com.bus.service.impl;

import com.bus.dto.LinePageQueryDTO;
import com.bus.entity.Line;
import com.bus.mapper.LineMapper;
import com.bus.mapper.VehicleMapper;
import com.bus.result.PageResult;
import com.bus.service.LineService;
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
public class LineServiceImpl implements LineService {

    @Autowired
    private LineMapper lineMapper;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public PageResult page(LinePageQueryDTO linePageQueryDTO) {
        // 设置默认分页参数
        int pageNum = linePageQueryDTO.getPage() != null ? linePageQueryDTO.getPage() : 1;
        int pageSize = linePageQueryDTO.getPageSize() != null ? linePageQueryDTO.getPageSize() : 10;
        PageHelper.startPage(pageNum, pageSize);
        Page<Line> page = lineMapper.selectAll(linePageQueryDTO);
        return PageResult.of(page.getTotal(), page.getResult(), pageNum, pageSize);
    }

    @Override
    public Line getById(Integer id) {
        return lineMapper.selectById(id);
    }

    @Override
    public void add(Line line) {
        lineMapper.insert(line);
    }

    @Override
    public void update(Line line) {
        lineMapper.update(line);
    }

    @Override
    public void deleteById(Integer id) {
        List<?> vehicles = vehicleMapper.selectByLineId(id);
        if (vehicles != null && !vehicles.isEmpty()) {
            throw new RuntimeException("该线路下还有 " + vehicles.size() + " 辆车辆，无法删除");
        }
        lineMapper.deleteById(id);
    }

    @Override
    public List<Line> getAll() {
        return lineMapper.selectAllList();
    }

    @Override
    public List<Line> getByFleetId(Integer fleetId) {
        return lineMapper.selectByFleetId(fleetId);
    }
}