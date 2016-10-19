package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.CarBrand;
import com.pujjr.base.vo.QueryParamCarBrandVo;

public interface CarBrandMapper {

	int deleteByPrimaryKey(String id);

    int insert(CarBrand record);

    int insertSelective(CarBrand record);

    CarBrand selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CarBrand record);

    int updateByPrimaryKey(CarBrand record);
    
    List<CarBrand> selectAll(@Param("params")QueryParamCarBrandVo params);
    
    CarBrand selectBrandBySerialId(@Param("carSerialId")String carSerialId); 
}