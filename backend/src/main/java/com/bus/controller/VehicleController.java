package com.bus.controller;

import com.bus.common.Result;
import com.bus.dto.VehiclePageQueryDTO;
import com.bus.entity.Vehicle;
import com.bus.result.PageResult;
import com.bus.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 122511851
 * @since 2026/5/31 11:39
 */
@RestController
@RequestMapping("/api/vehicle")
@Slf4j
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * 车辆分页查询
     * @param vehiclePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(VehiclePageQueryDTO vehiclePageQueryDTO) {
        log.info("车辆分页查询：{}", vehiclePageQueryDTO);
        PageResult pageResult = vehicleService.page(vehiclePageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Vehicle> getById(@PathVariable Integer id) {
        return Result.success(vehicleService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Vehicle vehicle) {
        vehicleService.add(vehicle);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody Vehicle vehicle) {
        vehicle.setVehicleId(id);
        vehicleService.update(vehicle);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Integer id) {
        vehicleService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<List<Vehicle>> getAll() {
        return Result.success(vehicleService.getAll());
    }

    @GetMapping("/line/{lineId}")
    public Result<List<Vehicle>> getByLineId(@PathVariable Integer lineId) {
        return Result.success(vehicleService.getByLineId(lineId));
    }
}
