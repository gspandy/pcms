package com.pujjr.postloan.vo;
/**
 * 当前案件应还项
 * **/
public class RepayFeeItemVo 
{
	//应还本金
	private double repayCapital;
	//应还利息
	private double repayInterest;
	//应还罚息
	private double repayOverdueAmount;
	//其他费用
	private double otherAmount;
	//其他费用罚息
	private double otherOverdueAmount;
	//剩余本金
	private double remainCapital;
	//挂账金额
	private double stayAmount;
	//应还总额
	private double totalRepayAmount;
	
	public RepayFeeItemVo()
	{
		this.remainCapital=0.00;
		this.repayInterest=0.00;
		this.repayOverdueAmount=0.00;
		this.otherAmount= 0.00;
		this.otherOverdueAmount = 0.00;
		this.stayAmount=0.00;
		this.totalRepayAmount=0.00;
	}
	
	public double getTotalRepayAmount() {
		return totalRepayAmount;
	}

	public void setTotalRepayAmount(double totalRepayAmount) {
		this.totalRepayAmount = totalRepayAmount;
	}

	public double getRepayCapital() {
		return repayCapital;
	}
	public void setRepayCapital(double repayCapital) {
		this.repayCapital = repayCapital;
	}
	public double getRepayInterest() {
		return repayInterest;
	}
	public void setRepayInterest(double repayInterest) {
		this.repayInterest = repayInterest;
	}
	public double getRepayOverdueAmount() {
		return repayOverdueAmount;
	}
	public void setRepayOverdueAmount(double repayOverdueAmount) {
		this.repayOverdueAmount = repayOverdueAmount;
	}
	public double getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(double otherAmount) {
		this.otherAmount = otherAmount;
	}
	public double getOtherOverdueAmount() {
		return otherOverdueAmount;
	}
	public void setOtherOverdueAmount(double otherOverdueAmount) {
		this.otherOverdueAmount = otherOverdueAmount;
	}
	public double getRemainCapital() {
		return remainCapital;
	}
	public void setRemainCapital(double remainCapital) {
		this.remainCapital = remainCapital;
	}
	public double getStayAmount() {
		return stayAmount;
	}
	public void setStayAmount(double stayAmount) {
		this.stayAmount = stayAmount;
	}
	
	
}
