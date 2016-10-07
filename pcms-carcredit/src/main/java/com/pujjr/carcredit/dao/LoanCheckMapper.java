package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.LoanCheck;

public interface LoanCheckMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoanCheck record);

    int insertSelective(LoanCheck record);

    LoanCheck selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoanCheck record);

    int updateByPrimaryKey(LoanCheck record);
}