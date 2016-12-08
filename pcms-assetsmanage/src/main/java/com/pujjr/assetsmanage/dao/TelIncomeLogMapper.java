package com.pujjr.assetsmanage.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.TelIncomeLog;

public interface TelIncomeLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(TelIncomeLog record);

    int insertSelective(TelIncomeLog record);

    TelIncomeLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TelIncomeLog record);

    int updateByPrimaryKey(TelIncomeLog record);
    
	List<HashMap<String,Object>> selectTelIncomeLogList(@Param("appId")String appId);
}