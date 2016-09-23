package com.pujjr.base.dao;

import java.util.List;

import com.pujjr.base.domain.ProductType;

public interface ProductTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductType record);

    int insertSelective(ProductType record);

    ProductType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductType record);

    int updateByPrimaryKey(ProductType record);
    
    List<ProductType> selectAll();
    
}