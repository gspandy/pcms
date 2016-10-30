package com.pujjr.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.domain.FormField;
import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.ProductPeriod;
import com.pujjr.base.domain.ProductRule;
import com.pujjr.base.domain.ProductSettle;
import com.pujjr.base.domain.ProductType;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.service.IProductService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.ProductPeriodVo;
import com.pujjr.base.vo.ProductSettleVo;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/product")
public class ProductController  extends BaseController
{
	@Autowired
	private IProductService productService;
	@Autowired
	private ISysBranchService sysBranchService;
	
	@RequestMapping(value="/producttype",method=RequestMethod.GET)
	public List<ProductType> getProductTypeList()
	{
		return productService.getProductTypeList();
	}
	@RequestMapping(value="/producttype",method=RequestMethod.POST)
	public void addProductType(@RequestBody ProductType productType)
	{
		productType.setId(Utils.get16UUID());
		productType.setCreateId("admin");
		productType.setCreateTime(new Date());
		productService.addProductType(productType);
	}
	
	@RequestMapping(value="/producttype/{id}",method=RequestMethod.DELETE)
	public void deleteProductType(@PathVariable String id)
	{
		productService.deleteProductType(id);
	}
	
	@RequestMapping(value="/producttype/{id}",method=RequestMethod.PUT)
	public void modifyProductType(@RequestBody ProductType productType)
	{
		productService.modifyProductType(productType);
	}
	@RequestMapping(value="/producttype/{productTypeId}/product")
	public  PageVo  getProductListByProductTypeId(@PathVariable String productTypeId,String curPage,String pageSize)
	{
		PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize),true);
		List<Product> list = productService.getProductListByProductTypeId(productTypeId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addProduct(@RequestBody Product product)
	{
		product.setId(Utils.get16UUID());
		product.setCreateId("admin");
		product.setCreateTime(new Date());
		productService.addProduct(product);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyProduct(@RequestBody Product product)
	{
		productService.modifyProduct(product);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteProduct(@PathVariable String id)
	{
		productService.deleteProduct(id);;
	}
	
	@RequestMapping(value="/productrule")
	public  PageVo  getProducRuletList(String curPage,String pageSize)
	{
		PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize),true);
		List<ProductRule> list = productService.getProductRuleList();
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/productrule",method=RequestMethod.POST)
	public void addProductRule(@RequestBody ProductRule productRule)
	{
		productRule.setId(Utils.get16UUID());
		productRule.setCreateId("admin");
		productRule.setCreateTime(new Date());
		productService.addProductRule(productRule);
	}
	@RequestMapping(value="/productrule/{id}",method=RequestMethod.PUT)
	public void modifyProductRule(@RequestBody ProductRule productRule)
	{
		productService.modifyProductRule(productRule);
	}
	@RequestMapping(value="/productrule/{id}",method=RequestMethod.DELETE)
	public void deleteProductRule(@PathVariable String id)
	{
		productService.deleteProductRule(id);;
	}
	@RequestMapping(value="/{productId}/productsettle",method=RequestMethod.GET)
	public List<ProductSettleVo> getProductSettleByProductId(@PathVariable String productId)
	{
		Product product =  productService.getProduct(productId);
		List<ProductSettleVo> rtnVo = new ArrayList<ProductSettleVo>();
		if(product.getProductSettleList()==null || product.getProductSettleList().size()==0)
		{
			for(int i = 1;i<=60;i++)
			{
				ProductSettleVo vo = new ProductSettleVo();
				vo.setPeriod(i);
				vo.setProductId(productId);
				vo.setReate(0.00);
				rtnVo.add(vo);
			}
		}
		else
		{
			List<ProductSettle> pos = product.getProductSettleList();
			for(int i = 1;i<=60;i++)
			{
				ProductSettleVo vo = new ProductSettleVo();
				vo.setPeriod(i);
				vo.setProductId(productId);
				vo.setReate(0.00);
				for(ProductSettle po : pos)
				{
					if(po.getPeriod()==i)
					{
						vo.setReate(po.getReate());
						break;
					}
				}
				rtnVo.add(vo);
			}
		}
		return rtnVo;
	}
	@RequestMapping(value="/{productId}/productsettle",method=RequestMethod.POST)
	public void saveProductSettle(@PathVariable String productId,@RequestBody List<ProductSettleVo> vos)
	{
		List<ProductSettle> list = new ArrayList<ProductSettle>();
		for(ProductSettleVo vo:vos)
		{
			ProductSettle po = new ProductSettle();
			po.setId(Utils.get16UUID());
			po.setPeriod(vo.getPeriod());
			po.setReate(vo.getReate());
			po.setProductId(vo.getProductId());
			list.add(po);
		}
		productService.batchSaveProductSettle(productId,list);
	}
	
	@RequestMapping(value="/{productId}/productperiod",method=RequestMethod.GET)
	public List<ProductPeriodVo> getProductPeriodByProductId(@PathVariable String productId)
	{
		Product product =  productService.getProduct(productId);
		List<ProductPeriodVo> rtnVo = new ArrayList<ProductPeriodVo>();
		if(product.getProductPeriodList()==null || product.getProductPeriodList().size()==0)
		{
			for(int i = 6;i<=60;i=i+6)
			{
				ProductPeriodVo vo = new ProductPeriodVo();
				vo.setPeriod(i);
				vo.setProductId(productId);
				vo.setFinanceFee(0.00);
				vo.setSelected(false);
				rtnVo.add(vo);
			}
		}
		else
		{
			List<ProductPeriod> pos = product.getProductPeriodList();
			for(int i = 6;i<=60;i=i+6)
			{
				ProductPeriodVo vo = new ProductPeriodVo();
				vo.setPeriod(i);
				vo.setProductId(productId);
				vo.setFinanceFee(0.00);
				vo.setSelected(false);
				for(ProductPeriod po : pos)
				{
					if(po.getPeriod()==i)
					{
						vo.setFinanceFee(po.getFinanceFee());
						vo.setSelected(true);
						break;
					}
				}
				rtnVo.add(vo);
			}
		}
		return rtnVo;
	}
	@RequestMapping(value="/{productId}/productperiod",method=RequestMethod.POST)
	public void saveProductPeriod(@PathVariable String productId,@RequestBody List<ProductPeriodVo> vos)
	{
		List<ProductPeriod> list = new ArrayList<ProductPeriod>();
		for(ProductPeriodVo vo:vos)
		{
			if(vo.getSelected())
			{
				ProductPeriod po = new ProductPeriod();
				po.setId(Utils.get16UUID());
				po.setPeriod(vo.getPeriod());
				po.setFinanceFee(vo.getFinanceFee());
				po.setProductId(vo.getProductId());
				list.add(po);
			}
		}
		productService.batchSaveProductPeriod(productId,list);
	}
	@RequestMapping(value="/enable",method=RequestMethod.GET)
	public List<Product> getAllEnableProductList()
	{
		return productService.getAllEnableProductList();
	}
	@RequestMapping(value="/branchenable",method=RequestMethod.GET)
	public List<Product> getBranchEnableProductList(HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		SysBranchDealer  sysBranchDealer = sysBranchService.getDealerByBranchId(account.getBranchId());
		if(sysBranchDealer == null)
		{
			throw new Exception("当前机构不是经销商不能提交订单");
		}
		return productService.getBranchEnableProductList(sysBranchDealer.getBranchId());
	}
	@RequestMapping(value="/branchenable/{branchCode}",method=RequestMethod.GET)
	public List<Product> getBranchEnableProductListByBranchCode(@PathVariable String branchCode)
	{
		SysBranch sysBranch = sysBranchService.getSysBranch(null, branchCode);
		return productService.getBranchEnableProductList(sysBranch.getId());
	}
	@RequestMapping(value="/getProductFormRequiredMap/{productId}",method=RequestMethod.GET)
	public HashMap<String,Object> getProductFormRequiredMap(@PathVariable String productId)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<FormField> requiredList = productService.getProductFormRequiredFieldList(productId);
		for(FormField item : requiredList)
		{
			map.put(item.getFieldId(), true);
		}
		return map;
	}
	@RequestMapping(value="/getAllProductList",method=RequestMethod.GET)
	public List<Product> getAllProductList()
	{
		return productService.getAllProductList();
	}
	
 }
