package com.pujjr.file.dao;

import com.pujjr.file.domain.DirectoryTemplateCategory;

public interface DirectoryTemplateCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryTemplateCategory record);

    int insertSelective(DirectoryTemplateCategory record);

    DirectoryTemplateCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryTemplateCategory record);

    int updateByPrimaryKey(DirectoryTemplateCategory record);
}