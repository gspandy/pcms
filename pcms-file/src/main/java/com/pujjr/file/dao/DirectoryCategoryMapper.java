package com.pujjr.file.dao;

import com.pujjr.file.domain.DirectoryCategory;

public interface DirectoryCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryCategory record);

    int insertSelective(DirectoryCategory record);

    DirectoryCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryCategory record);

    int updateByPrimaryKey(DirectoryCategory record);
}