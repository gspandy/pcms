package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(String id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    
    List<Product> selectAllByProductTypeId(@Param("productTypeId")String productTypeId);
    
    List<Product> selectAllEnableProductList();
    
    List<Product> selectBranchEnableProductList(String branchId);
    
    int deleteBranchProduct(String branchId);
    
    Product selectProductByProductCode(@Param("productCode")String productCode);
}