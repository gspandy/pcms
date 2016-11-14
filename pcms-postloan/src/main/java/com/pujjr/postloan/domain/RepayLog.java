package com.pujjr.postloan.domain;

import java.util.Date;

public class RepayLog {
    private String id;

	private String appId;

	private String feeType;

	private String feeRefId;

	private String chargeItem;

	private Double chargeAmount;

	private Date chargeDate;

	private String chargeMode;

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

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeRefId() {
		return feeRefId;
	}

	public void setFeeRefId(String feeRefId) {
		this.feeRefId = feeRefId;
	}

	public String getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(String chargeItem) {
		this.chargeItem = chargeItem;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public String getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
	}
}