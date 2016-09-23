package com.pujjr.base.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.pujjr.base.dao.ProductMapper;
import com.pujjr.base.dao.ProductPeriodMapper;
import com.pujjr.base.dao.ProductRuleMapper;
import com.pujjr.base.dao.ProductSettleMapper;
import com.pujjr.base.dao.ProductTypeMapper;
import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.ProductPeriod;
import com.pujjr.base.domain.ProductRule;
import com.pujjr.base.domain.ProductSettle;
import com.pujjr.base.domain.ProductType;
import com.pujjr.base.service.IProductService;
@Service
public class ProductServiceImpl implements IProductService 
{
	@Autowired
	private ProductMapper productDao;
	@Autowired
	private ProductTypeMapper productTypeDao;
	@Autowired
	private ProductRuleMapper productRuleDao;
	@Autowired
	private ProductSettleMapper productSettleDao;
	@Autowired
	private ProductPeriodMapper productPeriodDao;
	
	public List<ProductType> getProductTypeList() {
		// TODO Auto-generated method stub
		return productTypeDao.selectAll();
	}

	public void addProductType(ProductType record) {
		// TODO Auto-generated method stub
		productTypeDao.insert(record);
	}

	public void deleteProductType(String id) {
		// TODO Auto-generated method stub
		productTypeDao.deleteByPrimaryKey(id);
	}

	public void modifyProductType(ProductType record) {
		// TODO Auto-generated method stub
		productTypeDao.updateByPrimaryKey(record);
	}

	public List<Product> getProductListByProductTypeId(String id) {
		// TODO Auto-generated method stub
		return productDao.selectAllByProductTypeId(id);
	}

	public void addProduct(Product record) {
		// TODO Auto-generated method stub
		productDao.insert(record);
	}

	public void deleteProduct(String id) {
		// TODO Auto-generated method stub
		productDao.deleteByPrimaryKey(id);
	}

	public void modifyProduct(Product record) {
		// TODO Auto-generated method stub
		productDao.updateByPrimaryKey(record);
	}

	public List<ProductRule> getProductRuleList() {
		// TODO Auto-generated method stub
		return productRuleDao.selectAll();
	}

	public void addProductRule(ProductRule record) {
		// TODO Auto-generated method stub
		productRuleDao.insert(record);
	}

	public void modifyProductRule(ProductRule record) {
		// TODO Auto-generated method stub
		productRuleDao.updateByPrimaryKey(record);
	}

	public void deleteProductRule(String id) {
		// TODO Auto-generated method stub
		productRuleDao.deleteByPrimaryKey(id);
	}

	public Product getProduct(String id) {
		// TODO Auto-generated method stub
		Product product = productDao.selectByPrimaryKey(id);
		return product;
	}

	public void batchSaveProductSettle(String productId,List<ProductSettle> list) 
	{
		// TODO Auto-generated method stub
		productSettleDao.deleteByProductId(productId);
		for(ProductSettle l :list)
		{
			productSettleDao.insert(l);
		}
	}

	public void batchSaveProductPeriod(String productId,List<ProductPeriod> list) {
		// TODO Auto-generated method stub
		productPeriodDao.deleteByProductId(productId);
		for(ProductPeriod l :list)
		{
			productPeriodDao.insert(l);
		}
	}

	public List<Product> getBranchEnableProductList(String branchId) {
		// TODO Auto-generated method stub
		return productDao.selectBranchEnableProductList(branchId);
	}
	
	public List<Product> getAllEnableProductList()
	{
		return productDao.selectAllEnableProductList();
	}

	public void deleteBranchProduct(String branchId) {
		// TODO Auto-generated method stub
		productDao.deleteBranchProduct(branchId);
	}

	@Override
	public Product getProductByProductCode(String productCode) {
		// TODO Auto-generated method stub
		return productDao.selectProductByProductCode(productCode);
	}

}
