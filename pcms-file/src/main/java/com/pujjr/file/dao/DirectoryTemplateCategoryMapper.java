package com.pujjr.file.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.file.domain.DirectoryTemplateCategory;

public interface DirectoryTemplateCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(DirectoryTemplateCategory record);

    int insertSelective(DirectoryTemplateCategory record);

    DirectoryTemplateCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DirectoryTemplateCategory record);

    int updateByPrimaryKey(DirectoryTemplateCategory record);
    
    List<DirectoryTemplateCategory> selectUnContainListByTemplateId(@Param("templateId")String templateId,@Param("categoryIds")List<String> categoryIds);
    
    DirectoryTemplateCategory selectByTplIdAndCategoryId(@Param("tplId")String tplId,@Param("categoryId")String categoryId);
}