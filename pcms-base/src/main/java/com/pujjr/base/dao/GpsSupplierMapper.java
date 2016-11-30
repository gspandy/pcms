package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.GpsSupplier;

public interface GpsSupplierMapper {
    int deleteByPrimaryKey(String id);

	int insert(GpsSupplier record);

	int insertSelective(GpsSupplier record);

	GpsSupplier selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(GpsSupplier record);

	int updateByPrimaryKey(GpsSupplier record);
	
	List<GpsSupplier> selectAll(@Param("enabled")boolean enabled);
}