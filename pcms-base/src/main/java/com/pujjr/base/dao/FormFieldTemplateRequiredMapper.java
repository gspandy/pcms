package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.FormFieldTemplateRequiredKey;

public interface FormFieldTemplateRequiredMapper {
    int deleteByPrimaryKey(FormFieldTemplateRequiredKey key);

    int insert(FormFieldTemplateRequiredKey record);

    int insertSelective(FormFieldTemplateRequiredKey record);
   
    void deleteByTemplateId(@Param("templateId")String templateId);
}