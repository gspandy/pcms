package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.CarStyle;

public interface CarStyleMapper {

    int insert(CarStyle record);

    int insertSelective(CarStyle record);

    CarStyle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CarStyle record);

    int updateByPrimaryKey(CarStyle record);
    
    List<CarStyle> selectAllByCarSerialId(@Param("carSerialId")String carSerialId);
}