package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.GpsLvl;

public interface GpsLvlMapper {
    int deleteByPrimaryKey(String id);

    int insert(GpsLvl record);

    int insertSelective(GpsLvl record);

    GpsLvl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GpsLvl record);

    int updateByPrimaryKey(GpsLvl record);
    
    List<GpsLvl> selectByBranchIdAndRuleId(@Param("branchId")String branchId,@Param("gpsRuleId")String gpsRuleId);
    
    List<GpsLvl> selectAll();
    
    int deleteGpsLvlByBranchId(@Param("branchId")String branchId);
}