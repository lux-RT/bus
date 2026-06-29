package com.bus.mapper;

import com.bus.entity.ViolationRecord;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Mapper
public interface ViolationRecordMapper {

    List<ViolationRecord> selectList();

    ViolationRecord selectById(Integer id);

    int insert(ViolationRecord record);

    int update(ViolationRecord record);

    int deleteById(Integer id);

    Page<ViolationRecord> searchByCondition(@Param("keyword") String keyword,
                                           @Param("fleetId") Integer fleetId,
                                           @Param("lineId") Integer lineId,
                                           @Param("violationTypeId") Integer violationTypeId,
                                           @Param("stationId") Integer stationId,
                                           @Param("startTime") String startTime,
                                           @Param("endTime") String endTime);

    List<Map<String, Object>> selectStatsByFleet(@Param("startTime") String startTime,
                                                 @Param("endTime") String endTime);

    List<Map<String, Object>> selectStatsByLine(@Param("startTime") String startTime,
                                                 @Param("endTime") String endTime);

    List<Map<String, Object>> selectStatsByType(@Param("startTime") String startTime,
                                                 @Param("endTime") String endTime);

    List<Map<String, Object>> selectStatsByTime(@Param("startTime") String startTime,
                                                @Param("endTime") String endTime);
}