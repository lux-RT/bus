package com.bus.service.impl;

import com.bus.entity.ViolationRecord;
import com.bus.entity.ViolationType;
import com.bus.mapper.ViolationRecordMapper;
import com.bus.mapper.ViolationTypeMapper;
import com.bus.result.PageResult;
import com.bus.service.ViolationRecordService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Service
public class ViolationRecordServiceImpl implements ViolationRecordService {

    @Autowired
    private ViolationRecordMapper violationRecordMapper;

    @Autowired
    private ViolationTypeMapper violationTypeMapper;

    @Override
    public List<ViolationRecord> getList() {
        return violationRecordMapper.selectList();
    }

    @Override
    public ViolationRecord getById(Integer id) {
        return violationRecordMapper.selectById(id);
    }

    @Override
    public void add(ViolationRecord record) {
        violationRecordMapper.insert(record);
    }

    @Override
    public void update(ViolationRecord record) {
        violationRecordMapper.update(record);
    }

    @Override
    public void deleteById(Integer id) {
        violationRecordMapper.deleteById(id);
    }

    @Override
    public PageResult<ViolationRecord> search(String keyword, Integer fleetId, Integer lineId, Integer violationTypeId, Integer stationId, String startTime, String endTime, Integer page, Integer pageSize) {
        int pageNum = page != null ? page : 1;
        int size = pageSize != null ? pageSize : 10;
        PageHelper.startPage(pageNum, size);
        Page<ViolationRecord> result = violationRecordMapper.searchByCondition(keyword, fleetId, lineId, violationTypeId, stationId, startTime, endTime);
        return PageResult.of(result.getTotal(), result.getResult(), pageNum, size);
    }

    @Override
    public List<Map<String, Object>> getStatsByFleet(String startTime, String endTime) {
        return violationRecordMapper.selectStatsByFleet(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getStatsByLine(String startTime, String endTime) {
        return violationRecordMapper.selectStatsByLine(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getStatsByType(String startTime, String endTime) {
        return violationRecordMapper.selectStatsByType(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getStatsByTime(String startTime, String endTime) {
        return violationRecordMapper.selectStatsByTime(startTime, endTime);
    }

    @Override
    public List<ViolationType> getViolationTypes() {
        return violationTypeMapper.selectAll();
    }
}