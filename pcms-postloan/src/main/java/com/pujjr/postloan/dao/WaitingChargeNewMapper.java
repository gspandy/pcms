package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.WaitingChargeNew;

public interface WaitingChargeNewMapper {
    int deleteByPrimaryKey(String id);

    int insert(WaitingChargeNew record);

    int insertSelective(WaitingChargeNew record);

    WaitingChargeNew selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WaitingChargeNew record);

    int updateByPrimaryKey(WaitingChargeNew record);
}