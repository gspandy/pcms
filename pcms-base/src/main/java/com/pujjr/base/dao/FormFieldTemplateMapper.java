package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.FormFieldTemplate;

public interface FormFieldTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(FormFieldTemplate record);

    int insertSelective(FormFieldTemplate record);

    FormFieldTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FormFieldTemplate record);

    int updateByPrimaryKey(FormFieldTemplate record);
    
    List<FormFieldTemplate> selectAll();
    
}