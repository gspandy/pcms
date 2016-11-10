package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.ManualOffer;

public interface ManualOfferMapper {
    int deleteByPrimaryKey(String id);

    int insert(ManualOffer record);

    int insertSelective(ManualOffer record);

    ManualOffer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ManualOffer record);

    int updateByPrimaryKey(ManualOffer record);
}