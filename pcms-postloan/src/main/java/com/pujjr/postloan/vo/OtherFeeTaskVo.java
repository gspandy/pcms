package com.pujjr.postloan.vo;

import java.util.Date;
/**
 * 其他费用任务信息
 * **/
public class OtherFeeTaskVo 
{
	//主键
	private String id;
	//申请单号
	private String appId;
	//合同编号
	private String contractNo;
	//客户姓名
	private String custName;
	//身份证号
	private String idNo;
	//费用总额
	private double feeTotalAmount; 
	//申请日期
	private Date applyDate;
	//申请状态
	private String applyStatus;
	//申请备注
	private String applyComment;
	//当前任务节点名称
	private String taskName;
	//审批人
	private String assignee;
	//流程定义ID
	private String procDefId;
	//流程实例ID
	private String procInstId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getApplyComment() {
		return applyComment;
	}
	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public double getFeeTotalAmount() {
		return feeTotalAmount;
	}
	public void setFeeTotalAmount(double feeTotalAmount) {
		this.feeTotalAmount = feeTotalAmount;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
}
