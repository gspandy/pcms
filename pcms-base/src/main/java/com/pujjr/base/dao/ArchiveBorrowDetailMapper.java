package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.ArchiveBorrowDetail;

public interface ArchiveBorrowDetailMapper {
    int deleteByPrimaryKey(String id);

	int insert(ArchiveBorrowDetail record);

	int insertSelective(ArchiveBorrowDetail record);

	ArchiveBorrowDetail selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ArchiveBorrowDetail record);

	int updateByPrimaryKey(ArchiveBorrowDetail record);
	
	List<ArchiveBorrowDetail> selectAllByBorrowId(@Param("borrowId")String borrowId);
	
	int deleteByBorrowId(@Param("borrowId")String borrowId);
}