package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.ApplyExtendPeriod;

public interface ApplyExtendPeriodMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyExtendPeriod record);

	int insertSelective(ApplyExtendPeriod record);

	ApplyExtendPeriod selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyExtendPeriod record);

	int updateByPrimaryKey(ApplyExtendPeriod record);
}