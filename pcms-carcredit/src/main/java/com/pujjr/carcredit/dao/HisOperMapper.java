package com.pujjr.carcredit.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.HisOper;
import com.pujjr.carcredit.po.HisOperPo;

public interface HisOperMapper {

	int deleteByPrimaryKey(String id);

	int insert(HisOper record);

	int insertSelective(HisOper record);

	HisOper selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(HisOper record);

	int updateByPrimaryKey(HisOper record);

    HashMap<String,Object> selectCommon(HashMap<String,Object> condition);
    
    List<HisOperPo> selectHisOperList(@Param("appId")String appId, @Param("accountId")String accountId);
}