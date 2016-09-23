package com.pujjr.base.domain;

import java.util.Date;

public class SysAccount {
    private String id;

	private String accountId;

	private String accountName;

	private String password;

	private String branchId;

	private String postId;

	private String mobile;

	private String weixinId;

	private String email;

	private String status;

	private Date createTime;

	private String createId;

	private Date updateTime;

	private String updateId;

	private String reserver1;

	private String reserver2;

	private String reserver3;

	private String reserver4;

	private String reserver5;
	
	private SysJob sysJob;
	
	private SysDictData statusDict;

	public SysDictData getStatusDict() {
		return statusDict;
	}

	public void setStatusDict(SysDictData statusDict) {
		this.statusDict = statusDict;
	}

	public SysJob getSysJob() {
		return sysJob;
	}

	public void setSysJob(SysJob sysJob) {
		this.sysJob = sysJob;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
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

	public String getReserver5() {
		return reserver5;
	}

	public void setReserver5(String reserver5) {
		this.reserver5 = reserver5;
	}
}