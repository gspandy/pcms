package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.OfferSummaryHis;

public interface OfferSummaryHisMapper {
    int deleteByPrimaryKey(String id);

	int insert(OfferSummaryHis record);

	int insertSelective(OfferSummaryHis record);

	OfferSummaryHis selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(OfferSummaryHis record);

	int updateByPrimaryKey(OfferSummaryHis record);
}