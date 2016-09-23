package com.pujjr.carcredit.po;

import java.util.Date;

public class ApplyInfoPo {
	// 申请单号
	private String appId;
	// 产品名称
	private String productName;
	// 客户名称
	private String tenantName;
	// 客户身份证号
	private String tenantIdNo;
	// 融资总金额
	private Double totalFinanceAmt;
	// 融资期数
	private int period;
	// 录单时间
	private Date createTime;
	// 当前状态
	private String status;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantIdNo() {
		return tenantIdNo;
	}
	public void setTenantIdNo(String tenantIdNo) {
		this.tenantIdNo = tenantIdNo;
	}
	public Double getTotalFinanceAmt() {
		return totalFinanceAmt;
	}
	public void setTotalFinanceAmt(Double totalFinanceAmt) {
		this.totalFinanceAmt = totalFinanceAmt;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
