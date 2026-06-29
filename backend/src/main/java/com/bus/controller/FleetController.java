package com.bus.controller;

import com.bus.common.Result;
import com.bus.dto.FleetPageQueryDTO;
import com.bus.entity.Fleet;
import com.bus.result.PageResult;
import com.bus.service.FleetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 122511851
 * @since 2026/5/31 11:36
 */
@RestController
@RequestMapping("/api/fleet")
@Slf4j
public class FleetController {

    @Autowired
    private FleetService fleetService;

    /**
     * 车队分页查询
     * @param fleetPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(FleetPageQueryDTO fleetPageQueryDTO) {
        log.info("车队分页查询：{}", fleetPageQueryDTO);
        PageResult pageResult = fleetService.page(fleetPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Fleet> getById(@PathVariable Integer id) {
        return Result.success(fleetService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody @Validated Fleet fleet) {
        fleetService.add(fleet);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody Fleet fleet) {
        fleet.setFleetId(id);
        fleetService.update(fleet);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Integer id) {
        fleetService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<List<Fleet>> getAll() {
        return Result.success(fleetService.getAll());
    }
}
