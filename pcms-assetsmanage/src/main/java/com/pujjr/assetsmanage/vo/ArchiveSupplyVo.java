package com.pujjr.assetsmanage.vo;

import java.util.List;

import com.pujjr.assetsmanage.po.ArchiveTaskPo;
import com.pujjr.base.domain.ArchiveSupplyDetail;

/**
 * 归档补充资料信息
 * @author 150032
 *
 */
public class ArchiveSupplyVo 
{
	private ArchiveTaskPo archiveTask;
	
	private List<ArchiveSupplyDetail> supplyDetailList;
	
	private String comment;
	
	public ArchiveTaskPo getArchiveTask() {
		return archiveTask;
	}
	public void setArchiveTask(ArchiveTaskPo archiveTask) {
		this.archiveTask = archiveTask;
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
