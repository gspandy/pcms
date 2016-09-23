package com.pujjr.jbpm.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowNodeForm;

public interface WorkflowNodeFormMapper {

	int deleteByPrimaryKey(String id);

    int insert(WorkflowNodeForm record);

    int insertSelective(WorkflowNodeForm record);

    WorkflowNodeForm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkflowNodeForm record);

    int updateByPrimaryKey(WorkflowNodeForm record);
    
    int deleteByWorkflowVersionId(@Param("workflowVersionId")String workflowVersionId);
    
}