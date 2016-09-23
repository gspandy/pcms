package com.pujjr.base.dao;

import java.util.List;

import com.pujjr.base.domain.CarBrand;

public interface CarBrandMapper {

	int deleteByPrimaryKey(String id);

    int insert(CarBrand record);

    int insertSelective(CarBrand record);

    CarBrand selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CarBrand record);

    int updateByPrimaryKey(CarBrand record);
    
    public List<CarBrand> selectAll();
}