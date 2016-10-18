package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.FormField;

public interface FormFieldMapper {
    int deleteByPrimaryKey(String id);

    int insert(FormField record);

    int insertSelective(FormField record);

    FormField selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FormField record);

    int updateByPrimaryKey(FormField record);
    
    List<FormField>  selectAll();
    
    List<FormField> selectChildList(@Param("parentId")String parentId);
    
    List<FormField> selectTemplateRequireFieldList(@Param("tplId")String tplId);
}