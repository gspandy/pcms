package com.pujjr.jbpm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowRunPath;

public interface WorkflowRunPathMapper {
    int deleteByPrimaryKey(String id);

	int insert(WorkflowRunPath record);

	int insertSelective(WorkflowRunPath record);

	WorkflowRunPath selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(WorkflowRunPath record);

	int updateByPrimaryKey(WorkflowRunPath record);
    
    List<WorkflowRunPath> selectAllByProcInstIdAndActId(@Param("procInstId")String procInstId,@Param("actId")String actId);
    
    List<WorkflowRunPath> selectAllByProcInstId(@Param("procInstId")String procInstId);
}