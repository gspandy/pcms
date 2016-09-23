package com.pujjr.jbpm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowNodes;

public interface WorkflowNodesMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkflowNodes record);

    int insertSelective(WorkflowNodes record);

    WorkflowNodes selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkflowNodes record);

    int updateByPrimaryKey(WorkflowNodes record);
    
    List<WorkflowNodes> selectAllByNodeType(@Param("workflowVersionId")String workflowVersionId,@Param("nodeType")String nodeType);
    
    List<HashMap<String,Object>> selectWorkflowUserTaskNodeForms(@Param("workflowVersionId")String workflowVersionId);
    
    HashMap<String,Object> selectWorkflowNodeParam(@Param("workflowVersionId")String workflowVersionId, @Param("nodeId")String nodeId);
    
    int deleteNodesByVersionId(@Param("workflowVersionId")String workflowVersionId);
    
    WorkflowNodes selectWorkflowNodeByNodeId(@Param("workflowVersionId")String workflowVersionId,@Param("nodeId")String nodeId);
    
    List<HashMap<String,Object>> selectWorkflowUserTaskNodeAssignee(@Param("workflowVersionId")String workflowVersionId);
}