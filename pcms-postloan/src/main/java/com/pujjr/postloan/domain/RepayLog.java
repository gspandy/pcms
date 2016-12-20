package com.pujjr.postloan.domain;

import java.util.Date;

public class RepayLog {
    private String id;

	private String appId;

	private Integer seq;

	private String repayMode;

	private Double repayAmount;

	private Date repayTime;

	private String logStatus;

	private Double capital;

	private Double interest;

	private Double overdueAmount;

	private Double otherFee;

	private Double otherOverdueAmount;

	private Double extendFee;

	private Double lateFee;

	private Double stageAmount;

	private Double reserver1;

	private Double reserver2;

	private Double reserver3;

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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getRepayMode() {
		return repayMode;
	}

	public void setRepayMode(String repayMode) {
		this.repayMode = repayMode;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

	public String getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(Double overdueAmount) {
		this.overdueAmount = overdueAmount;
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

	public Double getExtendFee() {
		return extendFee;
	}

	public void setExtendFee(Double extendFee) {
		this.extendFee = extendFee;
	}

	public Double getLateFee() {
		return lateFee;
	}

	public void setLateFee(Double lateFee) {
		this.lateFee = lateFee;
	}

	public Double getStageAmount() {
		return stageAmount;
	}

	public void setStageAmount(Double stageAmount) {
		this.stageAmount = stageAmount;
	}

	public Double getReserver1() {
		return reserver1;
	}

	public void setReserver1(Double reserver1) {
		this.reserver1 = reserver1;
	}

	public Double getReserver2() {
		return reserver2;
	}

	public void setReserver2(Double reserver2) {
		this.reserver2 = reserver2;
	}

	public Double getReserver3() {
		return reserver3;
	}

	public void setReserver3(Double reserver3) {
		this.reserver3 = reserver3;
	}
}