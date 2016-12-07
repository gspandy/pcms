package com.pujjr.assetsmanage.dao;

import com.pujjr.assetsmanage.domain.TelInterview;

public interface TelInterviewMapper {
    int deleteByPrimaryKey(String id);

	int insert(TelInterview record);

	int insertSelective(TelInterview record);

	TelInterview selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(TelInterview record);

	int updateByPrimaryKey(TelInterview record);
}