package com.pujjr.jbpm.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowGlobalParam;

public interface WorkflowGlobalParamMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkflowGlobalParam record);

    int insertSelective(WorkflowGlobalParam record);

    WorkflowGlobalParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkflowGlobalParam record);

    int updateByPrimaryKey(WorkflowGlobalParam record);
    
    WorkflowGlobalParam selectByWorkflowVersionId(@Param("workflowVersionId")String workflowVersionId);
    
    int deleteByWorkflowVersionId(@Param("workflowVersionId")String workflowVersionId);
}