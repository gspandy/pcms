package com.pujjr.postloan.vo;

import java.util.Date;
import java.util.List;
/**
 * 变更还款日申请要素
 * **/
public class ApplyAlterRepayDateVo 
{
	//新还款计划
	private List<NewRepayPlanVo> repayPlanList;
	//变更还款日费项
	private AlterRepayDateFeeItemVo feeItem;
	//申请备注
	private String applyComment;
	//申请日期
	private Date applyDate;
	//原还款日
	private Date oldClosingDate;
	//新还款日
	private Date newClosingDate;
	//变更天数
	private int alterDay;
	
	public List<NewRepayPlanVo> getRepayPlanList() {
		return repayPlanList;
	}
	public void setRepayPlanList(List<NewRepayPlanVo> repayPlanList) {
		this.repayPlanList = repayPlanList;
	}
	public AlterRepayDateFeeItemVo getFeeItem() {
		return feeItem;
	}
	public void setFeeItem(AlterRepayDateFeeItemVo feeItem) {
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
	public Date getOldClosingDate() {
		return oldClosingDate;
	}
	public void setOldClosingDate(Date oldClosingDate) {
		this.oldClosingDate = oldClosingDate;
	}
	public Date getNewClosingDate() {
		return newClosingDate;
	}
	public void setNewClosingDate(Date newClosingDate) {
		this.newClosingDate = newClosingDate;
	}
	public int getAlterDay() {
		return alterDay;
	}
	public void setAlterDay(int alterDay) {
		this.alterDay = alterDay;
	}
}
