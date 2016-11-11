package com.pujjr.postloan.vo;

import java.util.Date;
/**
 * 还款计划
 * **/
public class NewRepayPlanVo 
{
	//期数
	private int period;
	//应还本金
	private double repayCapital;
	//应还利息
	private double repayCnterest;
	//剩余本金
	private double remainCapital;
	//起息日
	private Date valueDate;
	//结账日
	private Date closingDate;
	//应还总额
	private double repayTotalAmount;
	
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public double getRepayCapital() {
		return repayCapital;
	}
	public void setRepayCapital(double repayCapital) {
		this.repayCapital = repayCapital;
	}
	public double getRepayCnterest() {
		return repayCnterest;
	}
	public void setRepayCnterest(double repayCnterest) {
		this.repayCnterest = repayCnterest;
	}
	public double getRemainCapital() {
		return remainCapital;
	}
	public void setRemainCapital(double remainCapital) {
		this.remainCapital = remainCapital;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public Date getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}
	public double getRepayTotalAmount() {
		return repayTotalAmount;
	}
	public void setRepayTotalAmount(double repayTotalAmount) {
		this.repayTotalAmount = repayTotalAmount;
	}
	
	
	
}
