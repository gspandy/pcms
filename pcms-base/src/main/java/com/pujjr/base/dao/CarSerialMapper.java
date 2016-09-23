package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.CarSerial;

public interface CarSerialMapper {

	int deleteByPrimaryKey(String id);

    int insert(CarSerial record);

    int insertSelective(CarSerial record);

    CarSerial selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CarSerial record);

    int updateByPrimaryKey(CarSerial record);
    
    List<CarSerial> selectAllByCarBrandId(@Param("carBrandId")String carBrandId);
}