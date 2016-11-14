package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.OfferBatchInfo;

public interface OfferBatchInfoMapper {
    int deleteByPrimaryKey(String id);

	int insert(OfferBatchInfo record);

	int insertSelective(OfferBatchInfo record);

	OfferBatchInfo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(OfferBatchInfo record);

	int updateByPrimaryKey(OfferBatchInfo record);
}