package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.RepayLog;

public interface RepayLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(RepayLog record);

    int insertSelective(RepayLog record);

    RepayLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RepayLog record);

    int updateByPrimaryKey(RepayLog record);
}