package com.bus.mapper;

import com.bus.dto.StationPageQueryDTO;
import com.bus.entity.Station;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Mapper
public interface StationMapper {

    List<Station> selectList();

    Station selectById(Integer id);

    int insert(Station station);

    int update(Station station);

    int deleteById(Integer id);

    /**
     * 分页查询站点
     * @param dto
     * @return
     */
    Page<Station> selectAll(StationPageQueryDTO dto);

    List<Station> selectAllList();
}
