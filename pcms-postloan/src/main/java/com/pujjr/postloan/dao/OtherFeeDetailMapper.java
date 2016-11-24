package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.OtherFeeDetail;

public interface OtherFeeDetailMapper {
    int deleteByPrimaryKey(String id);

	int insert(OtherFeeDetail record);

	int insertSelective(OtherFeeDetail record);

	OtherFeeDetail selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(OtherFeeDetail record);

	int updateByPrimaryKey(OtherFeeDetail record);
}