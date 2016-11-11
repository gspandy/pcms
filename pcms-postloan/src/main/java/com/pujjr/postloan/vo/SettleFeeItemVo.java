package com.pujjr.postloan.vo;
/**
 * 提前结清相关费项
 * **/
public class SettleFeeItemVo extends RepayFeeItemVo 
{
	//结清本金
	private double  settleCapital;
	//结清金额
	private double  settleTotalAmount;
	//结清后本金
	private double  settleAfterAmount;
	//违约金
	private double lateFee;
	
	public double getLateFee() {
		return lateFee;
	}
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}
	public double getSettleCapital() {
		return settleCapital;
	}
	public void setSettleCapital(double settleCapital) {
		this.settleCapital = settleCapital;
	}
	public double getSettleAfterAmount() {
		return settleAfterAmount;
	}
	public void setSettleAfterAmount(double settleAfterAmount) {
		this.settleAfterAmount = settleAfterAmount;
	}
	public double getSettleTotalAmount() {
		return settleTotalAmount;
	}
	public void setSettleTotalAmount(double settleTotalAmount) {
		this.settleTotalAmount = settleTotalAmount;
	}
	
	
}
