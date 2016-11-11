package com.pujjr.postloan.vo;
/**
 * 展期费项
 * **/
public class ExtendPeriodFeeItemVo extends RepayFeeItemVo
{
	//新旧还款日利息
	private double newOldInterest;
	//新计息本金
	private double newCapital;
	//展期费用
	private double extendFee;
	
	public double getNewOldInterest() {
		return newOldInterest;
	}
	public void setNewOldInterest(double newOldInterest) {
		this.newOldInterest = newOldInterest;
	}
	public double getNewCapital() {
		return newCapital;
	}
	public void setNewCapital(double newCapital) {
		this.newCapital = newCapital;
	}
	public double getExtendFee() {
		return extendFee;
	}
	public void setExtendFee(double extendFee) {
		this.extendFee = extendFee;
	}
	
	
}
