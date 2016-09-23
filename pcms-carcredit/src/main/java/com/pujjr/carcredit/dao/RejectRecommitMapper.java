package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.RejectRecommit;

public interface RejectRecommitMapper {
    int deleteByPrimaryKey(String id);

    int insert(RejectRecommit record);

    int insertSelective(RejectRecommit record);

    RejectRecommit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RejectRecommit record);

    int updateByPrimaryKey(RejectRecommit record);
}