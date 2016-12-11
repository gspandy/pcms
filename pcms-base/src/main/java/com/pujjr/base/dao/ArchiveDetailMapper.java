package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.ArchiveDetail;

public interface ArchiveDetailMapper {
    int deleteByPrimaryKey(String id);

	int insert(ArchiveDetail record);

	int insertSelective(ArchiveDetail record);

	ArchiveDetail selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ArchiveDetail record);

	int updateByPrimaryKey(ArchiveDetail record);
    
    int deleteByArchiveTaskId(@Param("archiveTaskId")String archiveTaskId);
}