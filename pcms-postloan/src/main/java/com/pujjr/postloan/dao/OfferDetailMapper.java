package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.OfferDetail;

public interface OfferDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(OfferDetail record);

    int insertSelective(OfferDetail record);

    OfferDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OfferDetail record);

    int updateByPrimaryKey(OfferDetail record);
}