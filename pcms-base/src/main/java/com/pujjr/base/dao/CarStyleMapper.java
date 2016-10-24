package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.vo.QueryParamCarStyleVo;

public interface CarStyleMapper {

    int deleteByPrimaryKey(String id);

	int insert(CarStyle record);

	int insertSelective(CarStyle record);

	CarStyle selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(CarStyle record);

	int updateByPrimaryKey(CarStyle record);
    
    List<CarStyle> selectAll(@Param("param")QueryParamCarStyleVo param);
}