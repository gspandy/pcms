package com.pujjr.file.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.file.domain.DirectoryFile;

public interface DirectoryFileMapper {
    int deleteByPrimaryKey(String id);

	int insert(DirectoryFile record);

	int insertSelective(DirectoryFile record);

	DirectoryFile selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(DirectoryFile record);

	int updateByPrimaryKey(DirectoryFile record);
    
    List<DirectoryFile> selectAll(@Param("businessId")String businessId,@Param("dirId")String dirId);
   
}