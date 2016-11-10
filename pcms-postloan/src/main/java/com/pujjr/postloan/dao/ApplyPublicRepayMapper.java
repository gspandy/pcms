package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.ApplyPublicRepay;

public interface ApplyPublicRepayMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplyPublicRepay record);

    int insertSelective(ApplyPublicRepay record);

    ApplyPublicRepay selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplyPublicRepay record);

    int updateByPrimaryKey(ApplyPublicRepay record);
}