package com.pujjr.assetsmanage.vo;

import java.util.List;

import com.pujjr.base.domain.ArchiveSupply;
import com.pujjr.base.domain.ArchiveSupplyDetail;

public class ArchiveSupplyVo 
{
	//补充归档资料信息
	private ArchiveSupply supplyInfo;
	//补充归档资料明细
	private List<ArchiveSupplyDetail> supplyDetailList;
	
	public ArchiveSupply getSupplyInfo() {
		return supplyInfo;
	}
	public void setSupplyInfo(ArchiveSupply supplyInfo) {
		this.supplyInfo = supplyInfo;
	}
	public List<ArchiveSupplyDetail> getSupplyDetailList() {
		return supplyDetailList;
	}
	public void setSupplyDetailList(List<ArchiveSupplyDetail> supplyDetailList) {
		this.supplyDetailList = supplyDetailList;
	}
	
	
}
