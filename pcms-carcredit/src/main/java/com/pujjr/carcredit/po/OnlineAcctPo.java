package com.pujjr.carcredit.po;

public class OnlineAcctPo 
{	
	private String accountId;
	private String accountName;
	private int curTaskCnt;
	private int maxTaxkCnt;
	
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
	public int getCurTaskCnt() {
		return curTaskCnt;
	}
	public void setCurTaskCnt(int curTaskCnt) {
		this.curTaskCnt = curTaskCnt;
	}
	public int getMaxTaxkCnt() {
		return maxTaxkCnt;
	}
	public void setMaxTaxkCnt(int maxTaxkCnt) {
		this.maxTaxkCnt = maxTaxkCnt;
	}
	

}
