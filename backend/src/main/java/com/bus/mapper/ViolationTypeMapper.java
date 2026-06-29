package com.bus.mapper;

import com.bus.entity.ViolationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Mapper
public interface ViolationTypeMapper {

    List<ViolationType> selectAll();
}