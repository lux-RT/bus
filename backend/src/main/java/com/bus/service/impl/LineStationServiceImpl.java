package com.bus.service.impl;

import com.bus.entity.LineStation;
import com.bus.mapper.LineMapper;
import com.bus.mapper.LineStationMapper;
import com.bus.service.LineStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-06-28
 */
@Service
public class LineStationServiceImpl implements LineStationService {

    @Autowired
    private LineStationMapper lineStationMapper;

    @Autowired
    private LineMapper lineMapper;

    @Override
    public List<LineStation> getByLineId(Integer lineId) {
        return lineStationMapper.selectByLineId(lineId);
    }

    @Override
    @Transactional
    public void add(LineStation lineStation) {
        lineStationMapper.insert(lineStation);
        lineMapper.updateTotalStations(lineStation.getLineId());
    }

    @Override
    @Transactional
    public void deleteByLineAndStation(Integer lineId, Integer stationId) {
        lineStationMapper.deleteByLineAndStation(lineId, stationId);
        lineMapper.updateTotalStations(lineId);
    }
}
