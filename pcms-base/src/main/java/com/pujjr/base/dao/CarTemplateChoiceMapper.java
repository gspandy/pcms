package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.CarTemplateChoice;

public interface CarTemplateChoiceMapper {
    int deleteByPrimaryKey(String id);

	int insert(CarTemplateChoice record);

	int insertSelective(CarTemplateChoice record);

	CarTemplateChoice selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(CarTemplateChoice record);

	int updateByPrimaryKey(CarTemplateChoice record);
    
    List<CarTemplateChoice> selectByTemplateId(@Param("templateId")String templateId);
    
    int deleteByTemplateId(@Param("templateId")String templateId);
}