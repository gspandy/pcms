package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.ProductPeriod;

public interface ProductPeriodMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductPeriod record);

    int insertSelective(ProductPeriod record);

    ProductPeriod selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductPeriod record);

    int updateByPrimaryKey(ProductPeriod record);
    
    int deleteByProductId(@Param("productId")String productId);
}