package com.pujjr.assetsmanage.vo;

import java.util.List;

import com.pujjr.base.domain.ArchiveSupply;
import com.pujjr.base.domain.ArchiveSupplyDetail;

public class ReApplyArchiveSupplyVo 
{
	//原补充资料信息
	private ArchiveSupplyVo oldSupplyVo;
	//新补充资料项信息
	private List<ArchiveSupplyDetail> supplyDetailList;
	//新补充资料备注
	private String comment;
	
	public ArchiveSupplyVo getOldSupplyVo() {
		return oldSupplyVo;
	}
	public void setOldSupplyVo(ArchiveSupplyVo oldSupplyVo) {
		this.oldSupplyVo = oldSupplyVo;
	}
	public List<ArchiveSupplyDetail> getSupplyDetailList() {
		return supplyDetailList;
	}
	public void setSupplyDetailList(List<ArchiveSupplyDetail> supplyDetailList) {
		this.supplyDetailList = supplyDetailList;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
