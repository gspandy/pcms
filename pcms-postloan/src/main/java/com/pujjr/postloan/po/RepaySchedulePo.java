package com.pujjr.postloan.po;

import java.util.List;

import com.pujjr.postloan.domain.RepayPlan;

/**
 * 
 * @author tom
 */
public class RepaySchedulePo {
	private Double mortgageAmt;//贷款总额
	private Double repayAmt;//还款总额
	private Double interestAmt;//支付利息总款
	private Double monthRepay;//月均还款
	private Integer period;//贷款期数
	private List<RepayPlan> repayPlanList; //还款计划
	

	public List<RepayPlan> getRepayPlanList() {
		return repayPlanList;
	}
	public void setRepayPlanList(List<RepayPlan> repayPlanList) {
		this.repayPlanList = repayPlanList;
	}
	public void setMortgageAmt(Double mortgageAmt) {
		this.mortgageAmt = mortgageAmt;
	}
	public void setRepayAmt(Double repayAmt) {
		this.repayAmt = repayAmt;
	}
	public void setInterestAmt(Double interestAmt) {
		this.interestAmt = interestAmt;
	}
	public void setMonthRepay(Double monthRepay) {
		this.monthRepay = monthRepay;
	}
	public double getMortgageAmt() {
		return mortgageAmt;
	}
	public void setMortgageAmt(double mortgageAmt) {
		this.mortgageAmt = mortgageAmt;
	}
	public double getRepayAmt() {
		return repayAmt;
	}
	public void setRepayAmt(double repayAmt) {
		this.repayAmt = repayAmt;
	}
	public double getInterestAmt() {
		return interestAmt;
	}
	public void setInterestAmt(double interestAmt) {
		this.interestAmt = interestAmt;
	}
	public double getMonthRepay() {
		return monthRepay;
	}
	public void setMonthRepay(double monthRepay) {
		this.monthRepay = monthRepay;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
}
