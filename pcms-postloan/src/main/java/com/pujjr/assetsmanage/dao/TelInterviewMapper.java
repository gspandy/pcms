package com.pujjr.assetsmanage.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.TelInterview;

public interface TelInterviewMapper {
    int deleteByPrimaryKey(String id);

	int insert(TelInterview record);

	int insertSelective(TelInterview record);

	TelInterview selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(TelInterview record);

	int updateByPrimaryKey(TelInterview record);
	
	List<TelInterview> selectTelInterviewHisList(@Param("appId") String appId);
}