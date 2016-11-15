package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.BankInfo;

public interface BankInfoMapper {
    int deleteByPrimaryKey(String id);

	int insert(BankInfo record);

	int insertSelective(BankInfo record);

	BankInfo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(BankInfo record);

	int updateByPrimaryKey(BankInfo record);
    
    List<BankInfo> selectAll(@Param("enabled")boolean eanbled);
    
    List<BankInfo> selectAllUnionpayBankInfoList();
}