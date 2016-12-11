package com.pujjr.base.dao;

import com.pujjr.base.domain.ArchiveTask;

public interface ArchiveTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArchiveTask record);

    int insertSelective(ArchiveTask record);

    ArchiveTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArchiveTask record);

    int updateByPrimaryKey(ArchiveTask record);
}