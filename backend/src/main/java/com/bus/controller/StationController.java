package com.bus.controller;

import com.bus.common.Result;
import com.bus.dto.StationPageQueryDTO;
import com.bus.entity.Station;
import com.bus.result.PageResult;
import com.bus.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 122511851
 * @since 2026/5/31 11:38
 */
@RestController
@RequestMapping("/api/station")
@Slf4j
public class StationController {

    @Autowired
    private StationService stationService;

    /**
     * 站点分页查询
     * @param dto
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(StationPageQueryDTO dto) {
        log.info("站点分页查询：{}", dto);
        PageResult pageResult = stationService.page(dto);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Station> getById(@PathVariable Integer id) {
        return Result.success(stationService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Station station) {
        stationService.add(station);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody Station station) {
        station.setStationId(id);
        stationService.update(station);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Integer id) {
        stationService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<List<Station>> getAll() {
        return Result.success(stationService.getAll());
    }
}
