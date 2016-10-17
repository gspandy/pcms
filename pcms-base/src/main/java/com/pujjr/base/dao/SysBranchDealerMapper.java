package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysBranchDealer;

public interface SysBranchDealerMapper {
    int deleteByPrimaryKey(String id);

	int insert(SysBranchDealer record);

	int insertSelective(SysBranchDealer record);

	SysBranchDealer selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysBranchDealer record);

	int updateByPrimaryKey(SysBranchDealer record);
    
    SysBranchDealer selectByBranchId(@Param("branchId")String branchId);
}