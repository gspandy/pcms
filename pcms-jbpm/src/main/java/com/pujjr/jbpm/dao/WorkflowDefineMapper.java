package com.pujjr.jbpm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.WorkflowDefine;

public interface WorkflowDefineMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkflowDefine record);

    int insertSelective(WorkflowDefine record);

    WorkflowDefine selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkflowDefine record);

    int updateByPrimaryKey(WorkflowDefine record);
    
    List<HashMap> selectListByTypeId(@Param("workflowTypeId") String workflowTypeId);
    
    WorkflowDefine selectByVersionId(@Param("versionId")String versionId);
    
    WorkflowDefine selectByDefineKey(@Param("workflowKey")String workflowKey);
    
    int setActivateVersion(@Param("defineId")String defineId,@Param("versionId")String versionId);
}