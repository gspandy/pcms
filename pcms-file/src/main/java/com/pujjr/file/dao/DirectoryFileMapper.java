package com.pujjr.file.dao;

import com.pujjr.file.domain.DirectoryFile;

public interface DirectoryFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryFile record);

    int insertSelective(DirectoryFile record);

    DirectoryFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryFile record);

    int updateByPrimaryKey(DirectoryFile record);
}