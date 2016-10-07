package com.pujjr.carcredit.dao;

import java.util.HashMap;

import com.pujjr.carcredit.domain.HisOper;

public interface HisOperMapper {

	int deleteByPrimaryKey(String id);

	int insert(HisOper record);

	int insertSelective(HisOper record);

	HisOper selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(HisOper record);

	int updateByPrimaryKey(HisOper record);

	
    
    HashMap<String,Object> selectCommon(HashMap<String,Object> condition);
}