package com.pujjr.file.dao;

import com.pujjr.file.domain.DirectoryTemplateRefDirectory;

public interface DirectoryTemplateRefDirectoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryTemplateRefDirectory record);

    int insertSelective(DirectoryTemplateRefDirectory record);

    DirectoryTemplateRefDirectory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryTemplateRefDirectory record);

    int updateByPrimaryKey(DirectoryTemplateRefDirectory record);
}