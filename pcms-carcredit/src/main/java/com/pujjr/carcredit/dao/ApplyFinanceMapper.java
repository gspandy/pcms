package com.pujjr.carcredit.dao;

import java.util.List;

import com.pujjr.carcredit.domain.ApplyFinance;
import com.pujjr.carcredit.vo.ApplyFinanceVo;

public interface ApplyFinanceMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyFinance record);

	int insertSelective(ApplyFinance record);

	ApplyFinance selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyFinance record);

	int updateByPrimaryKey(ApplyFinance record);
	
	List<ApplyFinance> selectByAppId(String appId);
	
	List<ApplyFinance> selectListByAppId(String appId);
 
}