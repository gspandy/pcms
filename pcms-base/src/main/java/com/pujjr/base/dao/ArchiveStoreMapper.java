package com.pujjr.base.dao;

import com.pujjr.base.domain.ArchiveStore;

public interface ArchiveStoreMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArchiveStore record);

    int insertSelective(ArchiveStore record);

    ArchiveStore selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArchiveStore record);

    int updateByPrimaryKey(ArchiveStore record);
}