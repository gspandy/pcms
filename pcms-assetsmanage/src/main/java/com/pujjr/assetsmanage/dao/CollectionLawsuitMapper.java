package com.pujjr.assetsmanage.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.CollectionLawsuit;

public interface CollectionLawsuitMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectionLawsuit record);

    int insertSelective(CollectionLawsuit record);

    CollectionLawsuit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectionLawsuit record);

    int updateByPrimaryKey(CollectionLawsuit record);
    
    CollectionLawsuit selectByApplyId(@Param("applyId")String applyId);
}