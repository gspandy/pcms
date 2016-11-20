package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.WaitingChargeNew;

public interface WaitingChargeNewMapper {
    int deleteByPrimaryKey(String id);

    int insert(WaitingChargeNew record);

    int insertSelective(WaitingChargeNew record);

    WaitingChargeNew selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WaitingChargeNew record);

    int updateByPrimaryKey(WaitingChargeNew record);
    
    List<WaitingChargeNew>  selectList(@Param("applyType")String applyType,@Param("applyRefId")String applyRefId,@Param("feeType")String feeType);
}