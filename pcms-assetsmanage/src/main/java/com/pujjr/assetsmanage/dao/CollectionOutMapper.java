package com.pujjr.assetsmanage.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.CollectionOut;

public interface CollectionOutMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectionOut record);

    int insertSelective(CollectionOut record);

    CollectionOut selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectionOut record);

    int updateByPrimaryKey(CollectionOut record);
    
    CollectionOut selectByApplyId(@Param("applyId")String applyId);
}