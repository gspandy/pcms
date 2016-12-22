package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.OfferSummaryHis;

public interface OfferSummaryHisMapper {
    int deleteByPrimaryKey(String id);

	int insert(OfferSummaryHis record);

	int insertSelective(OfferSummaryHis record);

	OfferSummaryHis selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(OfferSummaryHis record);

	int updateByPrimaryKey(OfferSummaryHis record);
	
	List<OfferSummaryHis> selectByFeeId(@Param("feeId")String feeId);
}