package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.GpsRule;

public interface GpsRuleMapper {
    int deleteByPrimaryKey(String id);

    int insert(GpsRule record);

    int insertSelective(GpsRule record);

    GpsRule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GpsRule record);

    int updateByPrimaryKey(GpsRule record);
    
    List<GpsRule> selectAll();
    
    GpsRule selectByAmt(@Param("amt")double amt);
}