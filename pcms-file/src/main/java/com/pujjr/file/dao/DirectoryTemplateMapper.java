package com.pujjr.file.dao;

import com.pujjr.file.domain.DirectoryTemplate;

public interface DirectoryTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryTemplate record);

    int insertSelective(DirectoryTemplate record);

    DirectoryTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryTemplate record);

    int updateByPrimaryKey(DirectoryTemplate record);
}