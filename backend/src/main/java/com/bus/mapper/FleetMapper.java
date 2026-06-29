package com.bus.mapper;

import com.bus.dto.FleetPageQueryDTO;
import com.bus.entity.Fleet;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Mapper
public interface FleetMapper {

    /**
     * 分页查询车队
     * @param fleetPageQueryDTO
     * @return
     */
    Page<Fleet> selectAll(FleetPageQueryDTO fleetPageQueryDTO);

    Fleet selectById(Integer id);

    int insert(Fleet fleet);

    int update(Fleet fleet);

    int deleteById(Integer id);

    List<Fleet> selectAllList();
}