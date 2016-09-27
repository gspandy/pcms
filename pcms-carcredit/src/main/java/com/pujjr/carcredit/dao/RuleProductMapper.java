package com.pujjr.carcredit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.RuleProduct;

public interface RuleProductMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleProduct record);

    int insertSelective(RuleProduct record);

    RuleProduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleProduct record);

    int updateByPrimaryKey(RuleProduct record);
    
    List<RuleProduct> selectListByWorkgroupId(@Param("workgroupId")String workgroupId);
}