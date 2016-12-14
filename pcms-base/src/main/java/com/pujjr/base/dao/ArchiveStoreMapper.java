package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.ArchiveStore;

public interface ArchiveStoreMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArchiveStore record);

    int insertSelective(ArchiveStore record);

    ArchiveStore selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArchiveStore record);

    int updateByPrimaryKey(ArchiveStore record);
    
    ArchiveStore selectOne(@Param("appId")String appId,@Param("archiveType")String archiveType,@Param("fileName")String fileName);
    
    List<ArchiveStore> selectAll(@Param("appId")String appId);
}