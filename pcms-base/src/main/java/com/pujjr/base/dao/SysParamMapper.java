package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysParam;

public interface SysParamMapper {
    int deleteByPrimaryKey(String id);

	int insert(SysParam record);

	int insertSelective(SysParam record);

	SysParam selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysParam record);

	int updateByPrimaryKey(SysParam record);
	
	List<SysParam> selectList(@Param("paramName")String paramName);
	
	SysParam selectByParamName(@Param("paramName")String paramName);

}