package com.pujjr.base.dao;

import java.util.List;

import com.pujjr.base.domain.CarTemplate;

public interface CarTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(CarTemplate record);

    int insertSelective(CarTemplate record);

    CarTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CarTemplate record);

    int updateByPrimaryKey(CarTemplate record);
    
    List<CarTemplate> selectCarTemplateList();
}