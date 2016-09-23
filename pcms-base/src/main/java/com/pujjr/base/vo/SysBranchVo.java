package com.pujjr.base.vo;

import java.util.List;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;

public class SysBranchVo 
{
	private SysBranch sysBranch;
	
	private SysBranchDealer sysBranchDealer;
	
	private List<GpsRuleVo> gpsRuleVoList;
	
	private List<Product> productList;

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public SysBranch getSysBranch() {
		return sysBranch;
	}

	public void setSysBranch(SysBranch sysBranch) {
		this.sysBranch = sysBranch;
	}

	public SysBranchDealer getSysBranchDealer() {
		return sysBranchDealer;
	}

	public void setSysBranchDealer(SysBranchDealer sysBranchDealer) {
		this.sysBranchDealer = sysBranchDealer;
	}

	public List<GpsRuleVo> getGpsRuleVoList() {
		return gpsRuleVoList;
	}

	public void setGpsRuleVoList(List<GpsRuleVo> gpsRuleVoList) {
		this.gpsRuleVoList = gpsRuleVoList;
	}
	
	
}
