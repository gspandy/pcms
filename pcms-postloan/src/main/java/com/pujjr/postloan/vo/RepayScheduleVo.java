package com.pujjr.postloan.vo;

import java.util.List;

import com.pujjr.postloan.domain.RepayPlan;

/**
 * @author tom
 *
 */
public class RepayScheduleVo{
	private String mortgageAmt;//贷款总额
	private String repayAmt;//还款总额
	private String interestAmt;//支付利息总款
	private String monthRepay;//月均还款
	private String period;//贷款期数
	private List<RepayPlanVo> repayPlanList;
	
	
	public List<RepayPlanVo> getRepayPlanList() {
		return repayPlanList;
	}
	public void setRepayPlanList(List<RepayPlanVo> repayPlanList) {
		this.repayPlanList = repayPlanList;
	}
	public String getMortgageAmt() {
		return mortgageAmt;
	}
	public void setMortgageAmt(String mortgageAmt) {
		this.mortgageAmt = mortgageAmt;
	}
	public String getRepayAmt() {
		return repayAmt;
	}
	public void setRepayAmt(String repayAmt) {
		this.repayAmt = repayAmt;
	}
	public String getInterestAmt() {
		return interestAmt;
	}
	public void setInterestAmt(String interestAmt) {
		this.interestAmt = interestAmt;
	}
	public String getMonthRepay() {
		return monthRepay;
	}
	public void setMonthRepay(String monthRepay) {
		this.monthRepay = monthRepay;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}


}
