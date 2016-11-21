package com.pujjr.postloan.vo;

import java.util.List;

/**
 * 变更还款日费项
 * **/
public class AlterRepayDateFeeItemVo 
{
	//变更利息
	private double alterInterest;
	//新还款计划
	List<NewRepayPlanVo> newRepayPlanList;
	//变更天数
	private int alterDay;

	public List<NewRepayPlanVo> getNewRepayPlanList() {
		return newRepayPlanList;
	}

	public void setNewRepayPlanList(List<NewRepayPlanVo> newRepayPlanList) {
		this.newRepayPlanList = newRepayPlanList;
	}

	public double getAlterInterest() {
		return alterInterest;
	}

	public void setAlterInterest(double alterInterest) {
		this.alterInterest = alterInterest;
	}

	public int getAlterDay() {
		return alterDay;
	}

	public void setAlterDay(int alterDay) {
		this.alterDay = alterDay;
	}
}
