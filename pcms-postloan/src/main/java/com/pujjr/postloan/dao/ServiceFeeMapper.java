package com.pujjr.postloan.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ServiceFee;

public interface ServiceFeeMapper {
    int deleteByPrimaryKey(String id);

	int insert(ServiceFee record);

	int insertSelective(ServiceFee record);

	ServiceFee selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ServiceFee record);

	int updateByPrimaryKey(ServiceFee record);
	
	ServiceFee selectByApplyId(@Param("applyId")String applyId);
}