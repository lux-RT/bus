package com.bus.service;

import com.bus.entity.LineStation;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-06-28
 */
public interface LineStationService {

    List<LineStation> getByLineId(Integer lineId);

    void add(LineStation lineStation);

    void deleteByLineAndStation(Integer lineId, Integer stationId);
}
