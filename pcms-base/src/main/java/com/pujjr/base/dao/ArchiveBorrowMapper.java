package com.pujjr.base.dao;

import com.pujjr.base.domain.ArchiveBorrow;

public interface ArchiveBorrowMapper {
    int deleteByPrimaryKey(String id);

	int insert(ArchiveBorrow record);

	int insertSelective(ArchiveBorrow record);

	ArchiveBorrow selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ArchiveBorrow record);

	int updateByPrimaryKey(ArchiveBorrow record);
}