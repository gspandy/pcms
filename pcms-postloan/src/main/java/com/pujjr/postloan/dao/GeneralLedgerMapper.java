package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.GeneralLedger;

public interface GeneralLedgerMapper {
    int deleteByPrimaryKey(String id);

	int insert(GeneralLedger record);

	int insertSelective(GeneralLedger record);

	GeneralLedger selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(GeneralLedger record);

	int updateByPrimaryKey(GeneralLedger record);
}