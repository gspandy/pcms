package com.pujjr.assetsmanage.po;

import java.util.List;

import com.pujjr.base.domain.ArchiveTask;

public class ArchiveTaskPo extends ArchiveTask {
	
	private List<ArchiveDetailPo> detailList;

	public List<ArchiveDetailPo> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ArchiveDetailPo> detailList) {
		this.detailList = detailList;
	}
	
	
}
