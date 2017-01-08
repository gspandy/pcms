package com.pujjr.carcredit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.FraudHisResult;

public interface FraudHisResultMapper {
    int deleteByPrimaryKey(String id);

	int insert(FraudHisResult record);

	int insertSelective(FraudHisResult record);

	FraudHisResult selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(FraudHisResult record);

	int updateByPrimaryKey(FraudHisResult record);
    
    int deleteByAppId(@Param("appId")String appId,@Param("taskNodeName")String taskNodeName);
    
    List<FraudHisResult> selectListByAppId(@Param("appId")String appId,@Param("taskNodeName")String taskNodeName);
}