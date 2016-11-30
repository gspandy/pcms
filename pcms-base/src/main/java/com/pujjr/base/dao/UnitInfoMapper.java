package com.pujjr.base.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.UnitInfo;

public interface UnitInfoMapper {
    int deleteByPrimaryKey(String id);

	int insert(UnitInfo record);

	int insertSelective(UnitInfo record);

	UnitInfo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(UnitInfo record);

	int updateByPrimaryKey(UnitInfo record);
    
    List<HashMap<String,Object>> selectAll(@Param("enabled")boolean enabled,@Param("unitType")String unitType); 
}