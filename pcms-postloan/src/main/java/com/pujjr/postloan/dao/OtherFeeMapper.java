package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.OtherFee;

public interface OtherFeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(OtherFee record);

    int insertSelective(OtherFee record);

    OtherFee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OtherFee record);

    int updateByPrimaryKey(OtherFee record);
}