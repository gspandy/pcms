package com.pujjr.base.dao;

import com.pujjr.base.domain.ArchiveSupply;

public interface ArchiveSupplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArchiveSupply record);

    int insertSelective(ArchiveSupply record);

    ArchiveSupply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArchiveSupply record);

    int updateByPrimaryKey(ArchiveSupply record);
}