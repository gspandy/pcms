package com.pujjr.postloan.domain;

import java.util.Date;

public class ApplyPublicRepay {
    private String id;

	private String appId;

	private Double repayCapital;

	private Double repayInterest;

	private Double repayOverdueAmount;

	private Double otherFee;

	private Double otherOverdueAmount;

	private Double chargeAmount;

	private Double chargeDate;

	private String applyStatus;

	private String applyComment;

	private String chargeResult;

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

	public Double getRepayCapital() {
		return repayCapital;
	}

	public void setRepayCapital(Double repayCapital) {
		this.repayCapital = repayCapital;
	}

	public Double getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(Double repayInterest) {
		this.repayInterest = repayInterest;
	}

	public Double getRepayOverdueAmount() {
		return repayOverdueAmount;
	}

	public void setRepayOverdueAmount(Double repayOverdueAmount) {
		this.repayOverdueAmount = repayOverdueAmount;
	}

	public Double getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}

	public Double getOtherOverdueAmount() {
		return otherOverdueAmount;
	}

	public void setOtherOverdueAmount(Double otherOverdueAmount) {
		this.otherOverdueAmount = otherOverdueAmount;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Double getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Double chargeDate) {
		this.chargeDate = chargeDate;
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

	public String getChargeResult() {
		return chargeResult;
	}

	public void setChargeResult(String chargeResult) {
		this.chargeResult = chargeResult;
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