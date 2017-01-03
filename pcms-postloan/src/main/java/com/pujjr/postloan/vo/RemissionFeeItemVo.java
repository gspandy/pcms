package com.pujjr.postloan.vo;

import java.util.Date;

/**
 * 减免费项
 * **/
public class RemissionFeeItemVo 
{
	//减免本金
	private double capital;
	//减免利息
	private double interest;
	//减免罚息
	private double overdueAmount;
	//减免其他费用
	private double otherFee;
	//减免其他费用罚息
	private double otherOverdueAmount;
	//减免违约金
	private double lateFee;
	//减免日期
	private Date remissionDate;
	//减免备注
	private String remissionComment;
	
	public RemissionFeeItemVo()
	{
		this.capital=0.00;
		this.interest=0.00;
		this.overdueAmount = 0.00;
		this.otherFee=0.00;
		this.otherOverdueAmount=0.00;
		this.lateFee=0.00;
	}
	
	public Date getRemissionDate() {
		return remissionDate;
	}
	public void setRemissionDate(Date remissionDate) {
		this.remissionDate = remissionDate;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(double overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	public double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(double otherFee) {
		this.otherFee = otherFee;
	}
	public double getOtherOverdueAmount() {
		return otherOverdueAmount;
	}
	public void setOtherOverdueAmount(double otherOverdueAmount) {
		this.otherOverdueAmount = otherOverdueAmount;
	}
	public double getLateFee() {
		return lateFee;
	}
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public String getRemissionComment() {
		return remissionComment;
	}

	public void setRemissionComment(String remissionComment) {
		this.remissionComment = remissionComment;
	}
	
	
}
