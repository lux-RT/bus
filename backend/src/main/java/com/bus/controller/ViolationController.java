package com.bus.controller;

import com.bus.common.Result;
import com.bus.entity.ViolationRecord;
import com.bus.entity.ViolationType;
import com.bus.result.PageResult;
import com.bus.service.ViolationRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 122511851
 * @since 2026/5/31 11:40
 */
@RestController
@RequestMapping("/api/violation")
@Slf4j
public class ViolationController {

    @Autowired
    private ViolationRecordService violationRecordService;

    @GetMapping("/list")
    public Result<List<ViolationRecord>> getList() {
        return Result.success(violationRecordService.getList());
    }

    @GetMapping("/{id}")
    public Result<ViolationRecord> getById(@PathVariable Integer id) {
        return Result.success(violationRecordService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ViolationRecord violationRecord) {
        violationRecordService.add(violationRecord);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody ViolationRecord violationRecord) {
        violationRecord.setRecordId(id);
        violationRecordService.update(violationRecord);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Integer id) {
        violationRecordService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result<PageResult<ViolationRecord>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer fleetId,
            @RequestParam(required = false) Integer lineId,
            @RequestParam(required = false) Integer violationTypeId,
            @RequestParam(required = false) Integer stationId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(violationRecordService.search(keyword, fleetId, lineId, violationTypeId, stationId, startTime, endTime, page, pageSize));
    }

    @GetMapping("/stats/fleet")
    public Result<List<Map<String, Object>>> getStatsByFleet(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        return Result.success(violationRecordService.getStatsByFleet(startTime, endTime));
    }

    @GetMapping("/stats/line")
    public Result<List<Map<String, Object>>> getStatsByLine(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        return Result.success(violationRecordService.getStatsByLine(startTime, endTime));
    }

    @GetMapping("/stats/type")
    public Result<List<Map<String, Object>>> getStatsByType(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        return Result.success(violationRecordService.getStatsByType(startTime, endTime));
    }

    @GetMapping("/stats/time")
    public Result<List<Map<String, Object>>> getStatsByTime(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        return Result.success(violationRecordService.getStatsByTime(startTime, endTime));
    }

    @GetMapping("/types")
    public Result<List<ViolationType>> getViolationTypes() {
        return Result.success(violationRecordService.getViolationTypes());
    }
}
