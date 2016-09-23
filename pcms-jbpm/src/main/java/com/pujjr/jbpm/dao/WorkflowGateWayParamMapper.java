package com.pujjr.jbpm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowGateWayParam;

public interface WorkflowGateWayParamMapper {

	int deleteByPrimaryKey(String id);

    int insert(WorkflowGateWayParam record);

    int insertSelective(WorkflowGateWayParam record);

    WorkflowGateWayParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkflowGateWayParam record);

    int updateByPrimaryKey(WorkflowGateWayParam record);
    
    WorkflowGateWayParam selectByNodeId(
    					@Param("workflowVersionId")String workflowVersionId,
    					@Param("nodeId")String nodeId,
    					@Param("outNodeId")String outNodeId);
    int deleteByNodeId(@Param("workflowVersionId")String workflowVersionId,@Param("nodeId")String nodeId);
    
    List<WorkflowGateWayParam> selectGatewayParamsByNodeId(
						@Param("workflowVersionId")String workflowVersionId,
						@Param("nodeId")String nodeId); 
 }