package com.pujjr.jbpm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowVersion;

public interface WorkflowVersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkflowVersion record);

    int insertSelective(WorkflowVersion record);

    WorkflowVersion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkflowVersion record);

    int updateByPrimaryKey(WorkflowVersion record);
    
    WorkflowVersion selectByModelId(@Param("modelId")String modelId);
    
    List<HashMap<String,Object>> selectListByDefineId(@Param("defineId")String defineId);
    
    WorkflowVersion selectActivateVersionByDefineId(@Param("defineId")String defineId);
    
    
    
}