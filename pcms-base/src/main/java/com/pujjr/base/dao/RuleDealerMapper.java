package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.RuleDealer;

public interface RuleDealerMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleDealer record);

    int insertSelective(RuleDealer record);

    RuleDealer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleDealer record);

    int updateByPrimaryKey(RuleDealer record);
    
    List<SysBranch> selectListByWorkgroupId(@Param("workgroupId")String workgroupId);
    
    int deleteByWorkgroupId(@Param("workgroupId")String workgroupId);
}