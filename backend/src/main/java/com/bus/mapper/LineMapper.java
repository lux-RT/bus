package com.bus.mapper;

import com.bus.dto.LinePageQueryDTO;
import com.bus.entity.Line;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Mapper
public interface LineMapper {

    /**
     * 分页查询线路
     * @param linePageQueryDTO
     * @return
     */
    Page<Line> selectAll(LinePageQueryDTO linePageQueryDTO);

    Line selectById(Integer id);

    int insert(Line line);

    int update(Line line);

    int deleteById(Integer id);

    List<Line> selectAllList();

    List<Line> selectByFleetId(Integer fleetId);

    int updateTotalStations(@Param("lineId") Integer lineId);
}