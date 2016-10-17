package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysBranchGpsRuleKey;

public interface SysBranchGpsRuleMapper {
    int deleteByPrimaryKey(SysBranchGpsRuleKey key);

    int insert(SysBranchGpsRuleKey record);

    int insertSelective(SysBranchGpsRuleKey record);
    
    int getRecordSize(@Param("gpsLvlId")String gpslLvlId,@Param("gpsRuleId")String gpsRuleId);
    
}