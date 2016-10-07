package com.pujjr.carcredit.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.SignFinanceDetail;

public interface SignFinanceDetailMapper {
    int deleteByPrimaryKey(String id);

	int insert(SignFinanceDetail record);

	int insertSelective(SignFinanceDetail record);

	SignFinanceDetail selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SignFinanceDetail record);

	int updateByPrimaryKey(SignFinanceDetail record);
    
    SignFinanceDetail getByApplyFinanceId(@Param("applyFinanceId")String applyFinanceId);
    
    int deleteByAppId(@Param("appId")String appId);
}