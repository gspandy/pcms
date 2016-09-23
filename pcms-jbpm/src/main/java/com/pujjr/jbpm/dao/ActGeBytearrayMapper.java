package com.pujjr.jbpm.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.ActGeBytearray;

public interface ActGeBytearrayMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActGeBytearray record);

    int insertSelective(ActGeBytearray record);

    ActGeBytearray selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActGeBytearray record);

    int updateByPrimaryKeyWithBLOBs(ActGeBytearray record);

    int updateByPrimaryKey(ActGeBytearray record);
    
    ActGeBytearray selectBnpmByteByDeploymentId(@Param("deploymentId")String deploymentId);
    
    ActGeBytearray selectPngByteByDeploymentId(@Param("deploymentId")String deploymentId);
    
    HashMap<String,Object> selectModelByModelId(@Param("modelId")String modelId);
}