package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.ApplySettle;

public interface ApplySettleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplySettle record);

    int insertSelective(ApplySettle record);

    ApplySettle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplySettle record);

    int updateByPrimaryKey(ApplySettle record);
}