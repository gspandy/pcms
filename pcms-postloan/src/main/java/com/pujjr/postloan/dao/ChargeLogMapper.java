package com.pujjr.postloan.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ChargeLog;

public interface ChargeLogMapper {
    int deleteByPrimaryKey(String id);

	int insert(ChargeLog record);

	int insertSelective(ChargeLog record);

	ChargeLog selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ChargeLog record);

	int updateByPrimaryKey(ChargeLog record);
	
	ChargeLog selecOriginChargeLog(@Param("tranDate")Date tranDate,@Param("orderNo")String orderNo);
}