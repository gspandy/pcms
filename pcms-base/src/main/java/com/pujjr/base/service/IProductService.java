package com.pujjr.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.FormField;
import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.ProductPeriod;
import com.pujjr.base.domain.ProductRule;
import com.pujjr.base.domain.ProductSettle;
import com.pujjr.base.domain.ProductType;
@Service
@Transactional(rollbackFor=Exception.class)
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
	
	public List<FormField> getProductFormRequiredFieldList(String productId);
	
	public List<Product> getAllProductList();
	
	public double getProductSettleByPeriod(String productCode,int period);
	
	List<HashMap<String,Object>> getProductExtendPeriodList(String productCode,int curPeriod);
	
	
}
