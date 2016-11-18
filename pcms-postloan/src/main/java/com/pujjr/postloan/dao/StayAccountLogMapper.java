package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.StayAccountLog;

public interface StayAccountLogMapper {
    int deleteByPrimaryKey(String id);

	int insert(StayAccountLog record);

	int insertSelective(StayAccountLog record);

	StayAccountLog selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(StayAccountLog record);

	int updateByPrimaryKey(StayAccountLog record);
}