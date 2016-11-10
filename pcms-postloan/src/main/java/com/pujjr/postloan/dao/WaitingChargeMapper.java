package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.WaitingCharge;

public interface WaitingChargeMapper {
    int deleteByPrimaryKey(String id);

    int insert(WaitingCharge record);

    int insertSelective(WaitingCharge record);

    WaitingCharge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WaitingCharge record);

    int updateByPrimaryKey(WaitingCharge record);
}