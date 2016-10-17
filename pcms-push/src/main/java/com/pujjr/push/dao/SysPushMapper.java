package com.pujjr.push.dao;

import java.util.HashMap;
import java.util.List;

import com.pujjr.push.domain.SysPush;

public interface SysPushMapper {

	int deleteByPrimaryKey(String id);

	int insert(SysPush record);

	int insertSelective(SysPush record);

	SysPush selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysPush record);

	int updateByPrimaryKey(SysPush record);

    
    SysPush selectByAccountId(String accountId);
    List<SysPush> selectCommonList(HashMap<String,String> condition);
    SysPush selectCommon(HashMap<String,String> condition);
   
}