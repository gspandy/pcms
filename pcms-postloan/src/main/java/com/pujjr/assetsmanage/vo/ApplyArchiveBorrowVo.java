package com.pujjr.assetsmanage.vo;

import java.util.List;

import com.pujjr.base.domain.ArchiveBorrow;
import com.pujjr.base.domain.ArchiveBorrowDetail;

/**
 * 档案借阅申请参数
 * @author 150032
 *
 */
public class ApplyArchiveBorrowVo 
{
	//借阅信息
	private ArchiveBorrow borrowInfo;
	//借阅明细信息
	private List<ArchiveBorrowDetail> detailList;
	
	public ArchiveBorrow getBorrowInfo() {
		return borrowInfo;
	}
	public void setBorrowInfo(ArchiveBorrow borrowInfo) {
		this.borrowInfo = borrowInfo;
	}
	public List<ArchiveBorrowDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<ArchiveBorrowDetail> detailList) {
		this.detailList = detailList;
	}
	
}
