package com.pujjr.assetsmanage.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.CollectionRecover;

public interface CollectionRecoverMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectionRecover record);

    int insertSelective(CollectionRecover record);

    CollectionRecover selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectionRecover record);

    int updateByPrimaryKey(CollectionRecover record);

    CollectionRecover selectByApplyId(@Param("applyId")String applyId);
}