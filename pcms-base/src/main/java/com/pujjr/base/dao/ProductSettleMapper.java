package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.ProductSettle;

public interface ProductSettleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductSettle record);

    int insertSelective(ProductSettle record);

    ProductSettle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductSettle record);

    int updateByPrimaryKey(ProductSettle record);
    
    int deleteByProductId(@Param("productId")String productId);
    
    ProductSettle selectProductSettleByPeriod(@Param("productId")String productId,@Param("period")int period);
}