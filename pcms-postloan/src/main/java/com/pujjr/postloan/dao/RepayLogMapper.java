package com.pujjr.postloan.dao;


import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.RepayLog;

public interface RepayLogMapper {
    int deleteByPrimaryKey(String id);

	int insert(RepayLog record);

	int insertSelective(RepayLog record);

	RepayLog selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(RepayLog record);

	int updateByPrimaryKey(RepayLog record);
	/**
	 * 查询指定申请单总的还款记录数
	 * @param appId
	 * @return
	 */
	int selectRepayLogCntByAppId(@Param("appId")String appId);
}