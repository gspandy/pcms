package com.pujjr.postloan.dao;

import java.util.List;

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
	 * 查询申请对应费用类型的对应还款项目的还款记录
	 * @param appId
	 * @param feeType
	 * @param feeRefId
	 * @return
	 */
	List<RepayLog> selectList(@Param("appId")String appId,@Param("feeType")String feeType,@Param("feeRefId")String feeRefId,@Param("chargeItem")String chargeItem);
}