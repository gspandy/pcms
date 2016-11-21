package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.ApplyAlterRepayDate;

public interface ApplyAlterRepayDateMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyAlterRepayDate record);

	int insertSelective(ApplyAlterRepayDate record);

	ApplyAlterRepayDate selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyAlterRepayDate record);

	int updateByPrimaryKey(ApplyAlterRepayDate record);
}