package com.pujjr.assetsmanage.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.CollectionVisit;

public interface CollectionVisitMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectionVisit record);

    int insertSelective(CollectionVisit record);

    CollectionVisit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectionVisit record);

    int updateByPrimaryKey(CollectionVisit record);
    
    CollectionVisit selectByApplyId(@Param("applyId")String applyId);
}