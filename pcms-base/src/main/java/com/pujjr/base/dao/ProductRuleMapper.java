package com.pujjr.base.dao;

import java.util.List;

import com.pujjr.base.domain.ProductRule;

public interface ProductRuleMapper {
    int deleteByPrimaryKey(String id);

	int insert(ProductRule record);

	int insertSelective(ProductRule record);

	ProductRule selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ProductRule record);

	int updateByPrimaryKey(ProductRule record);
    
    List<ProductRule> selectAll();
}