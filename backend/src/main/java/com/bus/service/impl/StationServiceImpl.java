package com.bus.service.impl;

import com.bus.dto.StationPageQueryDTO;
import com.bus.entity.Station;
import com.bus.mapper.StationMapper;
import com.bus.result.PageResult;
import com.bus.service.StationService;
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
public class StationServiceImpl implements StationService {

    @Autowired
    private StationMapper stationMapper;

    @Override
    public PageResult page(StationPageQueryDTO dto) {
        // 设置默认分页参数
        int pageNum = dto.getPage() != null ? dto.getPage() : 1;
        int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10;
        PageHelper.startPage(pageNum, pageSize);
        Page<Station> page = stationMapper.selectAll(dto);
        return PageResult.of(page.getTotal(), page.getResult(), pageNum, pageSize);
    }

    @Override
    public Station getById(Integer id) {
        return stationMapper.selectById(id);
    }

    @Override
    public void add(Station station) {
        stationMapper.insert(station);
    }

    @Override
    public void update(Station station) {
        stationMapper.update(station);
    }

    @Override
    public void deleteById(Integer id) {
        stationMapper.deleteById(id);
    }

    @Override
    public List<Station> getAll() {
        return stationMapper.selectAllList();
    }
}
