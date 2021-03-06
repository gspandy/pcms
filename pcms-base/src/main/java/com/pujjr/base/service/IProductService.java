package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.ProductPeriod;
import com.pujjr.base.domain.ProductRule;
import com.pujjr.base.domain.ProductSettle;
import com.pujjr.base.domain.ProductType;
@Service
public interface IProductService 
{
	public List<ProductType> getProductTypeList();
	
	public void addProductType(ProductType record);
	
	public void deleteProductType(String id);
	
	public void modifyProductType(ProductType record);
	
	public List<Product> getProductListByProductTypeId(String id);
	
	public void addProduct(Product record);
	
	public void deleteProduct(String id);
	
	public void modifyProduct(Product record);
	
	public List<ProductRule> getProductRuleList();
	
	public void addProductRule(ProductRule record);
	
	public void modifyProductRule(ProductRule record);
	
	public void deleteProductRule(String id);
	
	public Product getProduct(String id);
	
	public void batchSaveProductSettle(String productId,List<ProductSettle> list);
	
	public void batchSaveProductPeriod(String productId,List<ProductPeriod> list);
	
	public List<Product> getBranchEnableProductList(String branchId);
	
	public List<Product> getAllEnableProductList();
	
	public void deleteBranchProduct(String branchId);
	
	public Product getProductByProductCode(String productCode);
	
}
