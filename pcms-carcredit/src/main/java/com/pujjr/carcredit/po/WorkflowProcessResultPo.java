package com.pujjr.carcredit.po;

import java.util.Date;

//流程处理实体
public class WorkflowProcessResultPo 
{
	//路径ID
	private String pathId;
	//活动节点ID
	private String actId;
	//活动节点名称
	private String actName;
	//处理人姓名
	private String assigneeName;
	//进入任务方式
	private String inJumpType;
	//跳转任务方式
	private String outJumpType;
	//任务分配时间
	private Date startTime;
	//开始处理时间
	private Date processTime;
	//处理完成时间
	private Date endTime;
	//处理信息
	private String message;
	//审批复核结果
	private String processResult;
	//处理意见
	private String processDesc;
	//备注
	private String comment;
	
	public String getPathId() {
		return pathId;
	}
	public void setPathId(String pathId) {
		this.pathId = pathId;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public String getInJumpType() {
		return inJumpType;
	}
	public void setInJumpType(String inJumpType) {
		this.inJumpType = inJumpType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getProcessResult() {
		return processResult;
	}
	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
	public String getProcessDesc() {
		return processDesc;
	}
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getOutJumpType() {
		return outJumpType;
	}
	public void setOutJumpType(String outJumpType) {
		this.outJumpType = outJumpType;
	}
}
