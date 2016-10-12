package com.pujjr.file.dao;

import com.pujjr.file.domain.Directory;

public interface DirectoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(Directory record);

    int insertSelective(Directory record);

    Directory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Directory record);

    int updateByPrimaryKey(Directory record);
}