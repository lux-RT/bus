package com.bus.controller;

import com.bus.common.Result;
import com.bus.dto.DriverPageQueryDTO;
import com.bus.entity.Driver;
import com.bus.result.PageResult;
import com.bus.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 122511851
 * @since 2026/5/31 11:33
 */
@RestController
@RequestMapping("/api/driver")
@Slf4j
public class DriverController {

    @Autowired
    private DriverService driverService;

    /**
     * 司机分页查询
     * @param driverPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(DriverPageQueryDTO driverPageQueryDTO) {
        log.info("司机分页查询：{}", driverPageQueryDTO);
        PageResult pageResult = driverService.page(driverPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Driver> getById(@PathVariable Integer id) {
        return Result.success(driverService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Driver driver) {
        driverService.add(driver);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody Driver driver) {
        driver.setDriverId(id);
        driverService.update(driver);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Integer id) {
        driverService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<List<Driver>> getAll() {
        return Result.success(driverService.getAll());
    }

    @GetMapping("/line/{lineId}")
    public Result<List<Driver>> getByLineId(@PathVariable Integer lineId) {
        return Result.success(driverService.getByLineId(lineId));
    }

    @GetMapping("/fleet/{fleetId}")
    public Result<List<Driver>> getByFleetId(@PathVariable Integer fleetId) {
        return Result.success(driverService.getByFleetId(fleetId));
    }
}
