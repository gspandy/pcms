package com.pujjr.file.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.file.domain.Directory;

public interface DirectoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(Directory record);

    int insertSelective(Directory record);

    Directory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Directory record);

    int updateByPrimaryKey(Directory record);
    
    List<Directory> selectAll(@Param("enabled")boolean enabled);
    
    List<Directory> selectSubDirectory(@Param("parentId")String parentId);
}