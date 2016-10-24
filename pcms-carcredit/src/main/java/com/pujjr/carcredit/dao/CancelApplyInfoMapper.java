package com.pujjr.carcredit.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.CancelApplyInfo;

public interface CancelApplyInfoMapper {
    int deleteByPrimaryKey(String id);

	int insert(CancelApplyInfo record);

	int insertSelective(CancelApplyInfo record);

	CancelApplyInfo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(CancelApplyInfo record);

	int updateByPrimaryKey(CancelApplyInfo record);
	
	CancelApplyInfo selectByRunPathId(@Param("runPathId")String runPathId);
}