package com.pujjr.carcredit.domain;

import java.util.Date;


public class Apply {
    private String id;

	private String appId;

	private String productCode;

	private Integer period;

	private String comment;

	private String status;

	private String createBranchCode;

	private String createAccountId;

	private Date createTime;

	private String custType;

	private Double totalFinanceAmt;

	private Double totalLoanAmt;

	private Double monthRent;

	private Date approveDate;

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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateBranchCode() {
		return createBranchCode;
	}

	public void setCreateBranchCode(String createBranchCode) {
		this.createBranchCode = createBranchCode;
	}

	public String getCreateAccountId() {
		return createAccountId;
	}

	public void setCreateAccountId(String createAccountId) {
		this.createAccountId = createAccountId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public Double getTotalFinanceAmt() {
		return totalFinanceAmt;
	}

	public void setTotalFinanceAmt(Double totalFinanceAmt) {
		this.totalFinanceAmt = totalFinanceAmt;
	}

	public Double getTotalLoanAmt() {
		return totalLoanAmt;
	}

	public void setTotalLoanAmt(Double totalLoanAmt) {
		this.totalLoanAmt = totalLoanAmt;
	}

	public Double getMonthRent() {
		return monthRent;
	}

	public void setMonthRent(Double monthRent) {
		this.monthRent = monthRent;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

}