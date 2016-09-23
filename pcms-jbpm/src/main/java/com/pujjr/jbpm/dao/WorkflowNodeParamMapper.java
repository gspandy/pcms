package com.pujjr.jbpm.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowNodeParam;

public interface WorkflowNodeParamMapper {

	int deleteByPrimaryKey(String id);

	int insert(WorkflowNodeParam record);

	int insertSelective(WorkflowNodeParam record);

	WorkflowNodeParam selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(WorkflowNodeParam record);

	int updateByPrimaryKey(WorkflowNodeParam record);
    
    int deleteByNodeId(@Param("workflowVersionId")String workflowVersionId,@Param("nodeId")String nodeId);
}