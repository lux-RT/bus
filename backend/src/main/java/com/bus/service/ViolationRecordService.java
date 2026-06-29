package com.bus.service;

import com.bus.entity.ViolationRecord;
import com.bus.entity.ViolationType;
import com.bus.result.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

public interface ViolationRecordService {

    List<ViolationRecord> getList();

    ViolationRecord getById(Integer id);

    void add(ViolationRecord record);

    void update(ViolationRecord record);

    void deleteById(Integer id);

    PageResult<ViolationRecord> search(String keyword, Integer fleetId, Integer lineId, Integer violationTypeId, Integer stationId, String startTime, String endTime, Integer page, Integer pageSize);

    List<Map<String, Object>> getStatsByFleet(String startTime, String endTime);

    List<Map<String, Object>> getStatsByLine(String startTime, String endTime);

    List<Map<String, Object>> getStatsByType(String startTime, String endTime);

    List<Map<String, Object>> getStatsByTime(String startTime, String endTime);

    List<ViolationType> getViolationTypes();
}