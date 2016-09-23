package com.pujjr.jbpm.dao;

import java.util.List;

import com.pujjr.jbpm.domain.WorkflowType;

public interface WorkflowTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkflowType record);

    int insertSelective(WorkflowType record);

    WorkflowType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkflowType record);

    int updateByPrimaryKey(WorkflowType record);
    
    List<WorkflowType> selectAll();
}