package com.pujjr.postloan.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.RemissionItem;

public interface RemissionItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(RemissionItem record);

    int insertSelective(RemissionItem record);

    RemissionItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RemissionItem record);

    int updateByPrimaryKey(RemissionItem record);
    
    RemissionItem selectByApplyId(@Param("applyType")String applyType,@Param("applyId")String applyId);
}