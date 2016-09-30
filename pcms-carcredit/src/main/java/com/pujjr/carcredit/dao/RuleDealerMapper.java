package com.pujjr.carcredit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.RuleDealer;

public interface RuleDealerMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleDealer record);

    int insertSelective(RuleDealer record);

    RuleDealer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleDealer record);

    int updateByPrimaryKey(RuleDealer record);
    
    List<RuleDealer> selectListByWorkgroupId(@Param("workgroupId")String workgroupId);
}