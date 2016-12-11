package com.pujjr.assetsmanage.vo;

import java.util.Date;

/**
 * 档案延期
 * @author dengpan
 *
 */
public class ArchiveDelayVo 
{
	//延期档案任务ID
	private String archiveTaskId;
	//延期截止日期
	private Date delayEndDate;
	
	public String getArchiveTaskId() {
		return archiveTaskId;
	}
	public void setArchiveTaskId(String archiveTaskId) {
		this.archiveTaskId = archiveTaskId;
	}
	public Date getDelayEndDate() {
		return delayEndDate;
	}
	public void setDelayEndDate(Date delayEndDate) {
		this.delayEndDate = delayEndDate;
	}
	
}
