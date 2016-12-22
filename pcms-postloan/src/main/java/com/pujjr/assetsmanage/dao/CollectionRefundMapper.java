package com.pujjr.assetsmanage.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.CollectionRefund;

public interface CollectionRefundMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectionRefund record);

    int insertSelective(CollectionRefund record);

    CollectionRefund selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectionRefund record);

    int updateByPrimaryKey(CollectionRefund record);
    
    CollectionRefund selectByApplyId(@Param("applyId")String applyId);
}