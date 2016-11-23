package com.pujjr.postloan.vo;

import java.util.Date;
import java.util.List;

/**
 * 申请展期要素
 * **/
public class ApplyExtendPeriodVo 
{
	//新还款计划
//	private List<NewRepayPlanVo> repayPlanList;
	//申请备注
	private String applyComment;
	//申请日期
	private Date applyDate;
	//展期期数
	private int extendPeriod;
	//原还款方式
	private String oldRepayMode;
	//新还款方式
	private String newRepayMode;
	//展期费项
	private ExtendPeriodFeeItemVo feeItem;
	
//	public List<NewRepayPlanVo> getRepayPlanList() {
//		return repayPlanList;
//	}
//	public void setRepayPlanList(List<NewRepayPlanVo> repayPlanList) {
//		this.repayPlanList = repayPlanList;
//	}
	
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
	public int getExtendPeriod() {
		return extendPeriod;
	}
	public void setExtendPeriod(int extendPeriod) {
		this.extendPeriod = extendPeriod;
	}
	public String getOldRepayMode() {
		return oldRepayMode;
	}
	public void setOldRepayMode(String oldRepayMode) {
		this.oldRepayMode = oldRepayMode;
	}
	public String getNewRepayMode() {
		return newRepayMode;
	}
	public void setNewRepayMode(String newRepayMode) {
		this.newRepayMode = newRepayMode;
	}
	public ExtendPeriodFeeItemVo getFeeItem() {
		return feeItem;
	}
	public void setFeeItem(ExtendPeriodFeeItemVo feeItem) {
		this.feeItem = feeItem;
	}
	
	
}
