package com.pujjr.carcredit.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.TaskProcessResult;

public interface TaskProcessResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(TaskProcessResult record);

    int insertSelective(TaskProcessResult record);

    TaskProcessResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TaskProcessResult record);

    int updateByPrimaryKey(TaskProcessResult record);
    
    TaskProcessResult selectByRunPathId(@Param("runPathId")String runPathId);
}