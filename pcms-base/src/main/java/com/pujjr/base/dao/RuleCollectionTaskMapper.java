package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.RuleCollectionTask;

public interface RuleCollectionTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleCollectionTask record);

    int insertSelective(RuleCollectionTask record);

    RuleCollectionTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleCollectionTask record);

    int updateByPrimaryKey(RuleCollectionTask record);
    
    List<RuleCollectionTask> selectByWorkgroupId(@Param("workgroupId")String workgroupId);
    
    void deleteByWorkgroupId(@Param("workgroupId")String workgroupId);
}