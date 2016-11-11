package com.pujjr.postloan.vo;

import java.util.Date;

/**
 * 对公还款申请要素
 * **/
public class ApplyPublicRepayVo 
{
	//应还金额
	private RepayFeeItemVo feeItem;
	//申请备注
	private String applyComment;
	//申请日期
	private Date applyDate;
	//还款金额
	private double chargeAmount;
	//还款日期
	private Date chargeDate;
	
	public RepayFeeItemVo getFeeItem() {
		return feeItem;
	}
	public void setFeeItem(RepayFeeItemVo feeItem) {
		this.feeItem = feeItem;
	}
	public String getApplyComment() {
		return applyComment;
	}
	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public Date getChargeDate() {
		return chargeDate;
	}
	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}
}
