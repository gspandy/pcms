package com.pujjr.assetsmanage.vo;
/**
 * 已放款档案归档申请
 * @author dengpan
 *
 */
public class ArchiveLogVo 
{
	//已放款档案整理任务ID
	private String taskId;
	//档案整理ID
	private String archiveTaskId;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getArchiveTaskId() {
		return archiveTaskId;
	}
	public void setArchiveTaskId(String archiveTaskId) {
		this.archiveTaskId = archiveTaskId;
	}
	
}
