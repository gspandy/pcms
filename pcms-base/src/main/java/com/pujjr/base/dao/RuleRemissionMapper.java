package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.RuleRemission;

public interface RuleRemissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleRemission record);

    int insertSelective(RuleRemission record);

    RuleRemission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleRemission record);

    int updateByPrimaryKey(RuleRemission record);
    
    RuleRemission selectByWorkgroupId(@Param("workgroupId")String workgroupId);
    
    void deleteByWorkgroupId(@Param("workgroupId")String workgroupId);
}