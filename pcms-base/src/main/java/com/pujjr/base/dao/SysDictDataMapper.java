package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysDictData;

public interface SysDictDataMapper {
    int deleteByPrimaryKey(String id);

	int insert(SysDictData record);

	int insertSelective(SysDictData record);

	SysDictData selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysDictData record);

	int updateByPrimaryKey(SysDictData record);
    
    List<SysDictData> selectAllWithFilter(@Param("dictTypeId")String dictTypeId,@Param("dictTypeCode")String dictTypeCode);
    
    SysDictData selectByDictDataCode(@Param("dictDataCode")String dictDataCode);
}