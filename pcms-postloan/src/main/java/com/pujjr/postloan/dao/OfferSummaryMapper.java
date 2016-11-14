package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.OfferSummary;

public interface OfferSummaryMapper {
    int deleteByPrimaryKey(String id);

	int insert(OfferSummary record);

	int insertSelective(OfferSummary record);

	OfferSummary selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(OfferSummary record);

	int updateByPrimaryKey(OfferSummary record);
}