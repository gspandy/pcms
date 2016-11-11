package com.pujjr.postloan.vo;

import java.util.Date;
/**
 * 对公还款任务信息
 * **/
public class PublicRepayTaskVo 
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
	//应还金额
	private double repayAmount;
	//还款金额
	private double chargeAmount;
	//还款日期
	private Date chargeDate;
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
	public double getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(double repayAmount) {
		this.repayAmount = repayAmount;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public Date getChargeDate() {
		return chargeDate;
	}
	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}
	
}
