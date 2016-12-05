package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.InsuranceHis;
import com.pujjr.base.po.InsuranceHisPo;

public interface InsuranceHisMapper {
    int deleteByPrimaryKey(String id);

	int insert(InsuranceHis record);

	int insertSelective(InsuranceHis record);

	InsuranceHis selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(InsuranceHis record);

	int updateByPrimaryKey(InsuranceHis record);
	
	List<InsuranceHisPo> selectBySignId(@Param("signId")String signId,@Param("type")String type);
	
	int deleteByAppId(@Param("appId")String appId);
}