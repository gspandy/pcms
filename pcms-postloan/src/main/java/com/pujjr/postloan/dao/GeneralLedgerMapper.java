package com.pujjr.postloan.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.enumeration.EInterestMode;

public interface GeneralLedgerMapper {
    int deleteByPrimaryKey(String id);

	int insert(GeneralLedger record);

	int insertSelective(GeneralLedger record);

	GeneralLedger selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(GeneralLedger record);

	int updateByPrimaryKey(GeneralLedger record);
	
	GeneralLedger selectByAppId(@Param("appId")String appId);
	
	List<GeneralLedger> selectByLoanDate(@Param("loanDate")Date loanDate);
	
	List<GeneralLedger> selectByOverdueDay(@Param("overdueDay")int overdueDay);
	
}