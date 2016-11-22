package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ApplyExtendPeriod;
import com.pujjr.postloan.vo.SettleTaskVo;

public interface ApplyExtendPeriodMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyExtendPeriod record);

	int insertSelective(ApplyExtendPeriod record);

	ApplyExtendPeriod selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyExtendPeriod record);

	int updateByPrimaryKey(ApplyExtendPeriod record);
	
	ApplyExtendPeriod selectByProcInstId(String procInstId);
	
}