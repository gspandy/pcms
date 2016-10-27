package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.CallBackResult;

public interface CallBackResultMapper {

	int deleteByPrimaryKey(String id);

	int insert(CallBackResult record);

	int insertSelective(CallBackResult record);

	CallBackResult selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(CallBackResult record);

	int updateByPrimaryKey(CallBackResult record);

}