package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.CheckResult;

public interface CheckResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(CheckResult record);

    int insertSelective(CheckResult record);

    CheckResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CheckResult record);

    int updateByPrimaryKey(CheckResult record);
}