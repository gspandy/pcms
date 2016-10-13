package com.pujjr.file.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.file.domain.DirectoryTemplateCategoryRefDirectory;

public interface DirectoryTemplateCategoryRefDirectoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryTemplateCategoryRefDirectory record);

    int insertSelective(DirectoryTemplateCategoryRefDirectory record);

    DirectoryTemplateCategoryRefDirectory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryTemplateCategoryRefDirectory record);

    int updateByPrimaryKey(DirectoryTemplateCategoryRefDirectory record);
    
    int deleteByTplCategoryId(@Param("tplCategoryId")String tplCategoryId);
}