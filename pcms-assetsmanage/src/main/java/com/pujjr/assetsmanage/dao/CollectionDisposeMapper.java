package com.pujjr.assetsmanage.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.CollectionDispose;

public interface CollectionDisposeMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectionDispose record);

    int insertSelective(CollectionDispose record);

    CollectionDispose selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectionDispose record);

    int updateByPrimaryKey(CollectionDispose record);
    
    CollectionDispose selectByApplyId(@Param("applyId")String applyId);
}