package com.pujjr.jbpm.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowNodeAssignee;

public interface WorkflowNodeAssigneeMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkflowNodeAssignee record);

    int insertSelective(WorkflowNodeAssignee record);

    WorkflowNodeAssignee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkflowNodeAssignee record);

    int updateByPrimaryKey(WorkflowNodeAssignee record);
    
    int deleteByWorkflowVersionId(@Param("workflowVersionId")String workflowVersionId);
    
    WorkflowNodeAssignee selectNodeAssignee(@Param("workflowVersionId")String workflowVersionId,@Param("nodeId")String nodeId);
}