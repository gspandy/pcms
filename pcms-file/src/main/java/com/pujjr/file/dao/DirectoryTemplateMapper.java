package com.pujjr.file.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.file.domain.DirectoryTemplate;

public interface DirectoryTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryTemplate record);

    int insertSelective(DirectoryTemplate record);

    DirectoryTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryTemplate record);

    int updateByPrimaryKey(DirectoryTemplate record);
    
    List<DirectoryTemplate> selectAll(@Param("enabled")boolean enabled);
}