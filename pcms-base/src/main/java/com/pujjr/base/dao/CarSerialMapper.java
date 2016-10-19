package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.CarSerial;
import com.pujjr.base.vo.QueryParamCarSerialVo;

public interface CarSerialMapper {

	int deleteByPrimaryKey(String id);

    int insert(CarSerial record);

    int insertSelective(CarSerial record);

    CarSerial selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CarSerial record);

    int updateByPrimaryKey(CarSerial record);
    
    List<CarSerial> selectAll(@Param("param")QueryParamCarSerialVo param);
}