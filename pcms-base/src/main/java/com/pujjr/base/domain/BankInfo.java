package com.pujjr.base.domain;

import java.util.Date;

public class BankInfo {
    private String id;

	private String bankName;

	private Boolean supportUnionpay;

	private String unionpayCode;

	private Boolean enabled;

	private Double maxChargeAmount;

	private String reserver1;

	private String reserver2;

	private String reserver3;

	private Double reserver4;

	private Double reserver5;

	private String createId;

	private Date createTime;

	private String updateId;

	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Boolean getSupportUnionpay() {
		return supportUnionpay;
	}

	public void setSupportUnionpay(Boolean supportUnionpay) {
		this.supportUnionpay = supportUnionpay;
	}

	public String getUnionpayCode() {
		return unionpayCode;
	}

	public void setUnionpayCode(String unionpayCode) {
		this.unionpayCode = unionpayCode;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Double getMaxChargeAmount() {
		return maxChargeAmount;
	}

	public void setMaxChargeAmount(Double maxChargeAmount) {
		this.maxChargeAmount = maxChargeAmount;
	}

	public String getReserver1() {
		return reserver1;
	}

	public void setReserver1(String reserver1) {
		this.reserver1 = reserver1;
	}

	public String getReserver2() {
		return reserver2;
	}

	public void setReserver2(String reserver2) {
		this.reserver2 = reserver2;
	}

	public String getReserver3() {
		return reserver3;
	}

	public void setReserver3(String reserver3) {
		this.reserver3 = reserver3;
	}

	public Double getReserver4() {
		return reserver4;
	}

	public void setReserver4(Double reserver4) {
		this.reserver4 = reserver4;
	}

	public Double getReserver5() {
		return reserver5;
	}

	public void setReserver5(Double reserver5) {
		this.reserver5 = reserver5;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}