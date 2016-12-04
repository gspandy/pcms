package com.pujjr.assetsmanage.vo;
/**
 * 变更还款银行信息值对象
 * @author dengpan
 *
 */
public class AlterBankInfoVo 
{
	//变更申请单号
	private String appId;
	//新还款银行ID
	private String repayBankId;
	//新还款账号
	private String repayAcctNo;
	//变更备注
	private String alterComment;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getRepayBankId() {
		return repayBankId;
	}
	public void setRepayBankId(String repayBankId) {
		this.repayBankId = repayBankId;
	}
	public String getRepayAcctNo() {
		return repayAcctNo;
	}
	public void setRepayAcctNo(String repayAcctNo) {
		this.repayAcctNo = repayAcctNo;
	}
	public String getAlterComment() {
		return alterComment;
	}
	public void setAlterComment(String alterComment) {
		this.alterComment = alterComment;
	}
	
	
}
