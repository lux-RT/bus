package com.bus.mapper;

import com.bus.entity.LineStation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Mapper
public interface LineStationMapper {

    List<LineStation> selectByLineId(Integer lineId);

    int insert(LineStation lineStation);

    int deleteByLineAndStation(Integer lineId, Integer stationId);

    int deleteByLineId(Integer lineId);
}