package com.bus.controller;

import com.bus.common.Result;
import com.bus.dto.LinePageQueryDTO;
import com.bus.entity.Driver;
import com.bus.entity.Line;
import com.bus.entity.LineStation;
import com.bus.result.PageResult;
import com.bus.service.DriverService;
import com.bus.service.LineService;
import com.bus.service.LineStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 122511851
 * @since 2026/5/31 11:38
 */
@RestController
@RequestMapping("/api/line")
@Slf4j
public class LineController {

    @Autowired
    private LineService lineService;

    @Autowired
    private LineStationService lineStationService;

    @Autowired
    private DriverService driverService;

    /**
     * 线路分页查询
     * @param linePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(LinePageQueryDTO linePageQueryDTO) {
        log.info("线路分页查询：{}", linePageQueryDTO);
        PageResult pageResult = lineService.page(linePageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Line> getById(@PathVariable Integer id) {
        return Result.success(lineService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Line line) {
        lineService.add(line);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody Line line) {
        line.setLineId(id);
        lineService.update(line);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Integer id) {
        lineService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<List<Line>> getAll() {
        return Result.success(lineService.getAll());
    }

    @GetMapping("/fleet/{fleetId}")
    public Result<List<Line>> getByFleetId(@PathVariable Integer fleetId) {
        return Result.success(lineService.getByFleetId(fleetId));
    }

    // ===== 线路站点管理 =====

    @GetMapping("/{lineId}/stations")
    public Result<List<LineStation>> getStations(@PathVariable Integer lineId) {
        return Result.success(lineStationService.getByLineId(lineId));
    }

    @PostMapping("/{lineId}/stations")
    public Result<Void> addStation(@PathVariable Integer lineId, @RequestBody LineStation lineStation) {
        lineStation.setLineId(lineId);
        lineStationService.add(lineStation);
        return Result.success();
    }

    @DeleteMapping("/{lineId}/stations/{stationId}")
    public Result<Void> removeStation(@PathVariable Integer lineId, @PathVariable Integer stationId) {
        lineStationService.deleteByLineAndStation(lineId, stationId);
        return Result.success();
    }

    @PutMapping("/{lineId}/road-captain/{driverId}")
    public Result<Void> setRoadCaptain(@PathVariable Integer lineId, @PathVariable Integer driverId) {
        // 取消该线路现有路队长的身份
        List<Driver> existingCaptains = driverService.getByLineId(lineId);
        if (existingCaptains != null) {
            for (Driver d : existingCaptains) {
                if (d.getIsRoadCaptain() != null && d.getIsRoadCaptain()) {
                    d.setIsRoadCaptain(false);
                    driverService.update(d);
                }
            }
        }
        // 设置新路队长
        Driver newCaptain = driverService.getById(driverId);
        if (newCaptain != null) {
            newCaptain.setIsRoadCaptain(true);
            newCaptain.setLineId(lineId);
            driverService.update(newCaptain);
        }
        return Result.success();
    }
}
