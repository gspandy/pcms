package com.pujjr.sms.domain;

import java.util.Date;

public class SmsHis {
    private String id;

	private String appId;

	private String type;

	private String mobile;

	private String content;

	private String createId;

	private Date createTime;

	private Date sendTime;

	private Date retTime;

	private String sendStatus;

	private Integer resendCnt;

	private String supplyName;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getRetTime() {
		return retTime;
	}

	public void setRetTime(Date retTime) {
		this.retTime = retTime;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getResendCnt() {
		return resendCnt;
	}

	public void setResendCnt(Integer resendCnt) {
		this.resendCnt = resendCnt;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
}