package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.ChargeLog;

public interface ChargeLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChargeLog record);

    int insertSelective(ChargeLog record);

    ChargeLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChargeLog record);

    int updateByPrimaryKey(ChargeLog record);
}