package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.StayAccount;

public interface StayAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(StayAccount record);

    int insertSelective(StayAccount record);

    StayAccount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StayAccount record);

    int updateByPrimaryKey(StayAccount record);
}