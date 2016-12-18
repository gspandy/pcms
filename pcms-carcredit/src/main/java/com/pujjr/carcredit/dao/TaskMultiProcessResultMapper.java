package com.pujjr.carcredit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.TaskMultiProcessResult;

public interface TaskMultiProcessResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(TaskMultiProcessResult record);

    int insertSelective(TaskMultiProcessResult record);

    TaskMultiProcessResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TaskMultiProcessResult record);

    int updateByPrimaryKey(TaskMultiProcessResult record);
    
    List<TaskMultiProcessResult> selectByRunPathId(@Param("runPathId")String runPathId);
}