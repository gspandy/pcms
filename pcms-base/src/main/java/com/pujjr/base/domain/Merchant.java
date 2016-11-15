package com.pujjr.base.domain;

import java.util.Date;

public class Merchant {
    private String id;

	private String merchantName;

	private String merchantNo;

	private String bankId;

	private String reserver1;

	private String reserver2;

	private String reserver3;

	private String reserver4;

	private Boolean enabled;

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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
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

	public String getReserver4() {
		return reserver4;
	}

	public void setReserver4(String reserver4) {
		this.reserver4 = reserver4;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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