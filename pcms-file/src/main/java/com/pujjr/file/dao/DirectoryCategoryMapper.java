package com.pujjr.file.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.file.domain.DirectoryCategory;

public interface DirectoryCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryCategory record);

    int insertSelective(DirectoryCategory record);

    DirectoryCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryCategory record);

    int updateByPrimaryKey(DirectoryCategory record);
    
    List<DirectoryCategory>  selectAll();
    
    List<DirectoryCategory> selectCatetoryByTemplateId(@Param("templateId")String templateId);
}