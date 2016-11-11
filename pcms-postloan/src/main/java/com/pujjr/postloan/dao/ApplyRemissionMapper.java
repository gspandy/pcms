package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.ApplyRemission;

public interface ApplyRemissionMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyRemission record);

	int insertSelective(ApplyRemission record);

	ApplyRemission selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyRemission record);

	int updateByPrimaryKey(ApplyRemission record);
}