package com.pujjr.postloan.domain;

import java.util.Date;

public class ApplyRefund {
    private String id;

	private String appId;

	private Double stayAmount;

	private Integer stayDay;

	private Double refundAmount;

	private Date refundDate;

	private String applyComment;

	private String applyStatus;

	private String createId;

	private Date createDate;

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

	public Double getStayAmount() {
		return stayAmount;
	}

	public void setStayAmount(Double stayAmount) {
		this.stayAmount = stayAmount;
	}

	public Integer getStayDay() {
		return stayDay;
	}

	public void setStayDay(Integer stayDay) {
		this.stayDay = stayDay;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getApplyComment() {
		return applyComment;
	}

	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
}