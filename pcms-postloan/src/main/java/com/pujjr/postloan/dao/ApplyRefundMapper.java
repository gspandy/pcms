package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.ApplyRefund;

public interface ApplyRefundMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyRefund record);

	int insertSelective(ApplyRefund record);

	ApplyRefund selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyRefund record);

	int updateByPrimaryKey(ApplyRefund record);
}