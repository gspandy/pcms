package com.pujjr.carcredit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.FraudInnerResult;

public interface FraudInnerResultMapper {
    int deleteByPrimaryKey(String id);

	int insert(FraudInnerResult record);

	int insertSelective(FraudInnerResult record);

	FraudInnerResult selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(FraudInnerResult record);

	int updateByPrimaryKey(FraudInnerResult record);
    
    List<FraudInnerResult> selectByAppId(@Param("appId")String appId);
    
    int deleteByAppId(@Param("appId")String appId);
}