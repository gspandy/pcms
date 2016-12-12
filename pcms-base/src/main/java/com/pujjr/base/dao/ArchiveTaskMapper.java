package com.pujjr.base.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.ArchiveTask;

public interface ArchiveTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArchiveTask record);

    int insertSelective(ArchiveTask record);

    ArchiveTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArchiveTask record);

    int updateByPrimaryKey(ArchiveTask record);
    
    List<ArchiveTask> selectArchiveTaskOverdueList(@Param("archiveType")String archiveType,@Param("endDate")Date endDate);
}