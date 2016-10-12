package com.pujjr.file.dao;

import com.pujjr.file.domain.DirectoryTemplateCategoryRefDirectory;

public interface DirectoryTemplateCategoryRefDirectoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryTemplateCategoryRefDirectory record);

    int insertSelective(DirectoryTemplateCategoryRefDirectory record);

    DirectoryTemplateCategoryRefDirectory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryTemplateCategoryRefDirectory record);

    int updateByPrimaryKey(DirectoryTemplateCategoryRefDirectory record);
}