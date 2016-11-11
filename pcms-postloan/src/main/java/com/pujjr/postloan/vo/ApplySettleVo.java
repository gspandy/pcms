package com.pujjr.postloan.vo;

import java.util.Date;
import java.util.List;
/**
 * 申请结清要素
 * **/
public class ApplySettleVo
{
	//结清相关费项
	private  SettleFeeItemVo feeItem;
	//新还款计划
	private List<NewRepayPlanVo> repayPlanList;
	//结清开始期数
	private int beginPeriod;
	//结清截止期数
	private int endPeriod;
	//申请备注
	private String applyComment;
	//申请有效截止日期
	private Date applyEffectDate;
	//申请日期
	private Date applyDate;
	
	public List<NewRepayPlanVo> getRepayPlanList() {
		return repayPlanList;
	}
	public void setRepayPlanList(List<NewRepayPlanVo> repayPlanList) {
		this.repayPlanList = repayPlanList;
	}
	
	public SettleFeeItemVo getFeeItem() {
		return feeItem;
	}
	public void setFeeItem(SettleFeeItemVo feeItem) {
		this.feeItem = feeItem;
	}
	public int getBeginPeriod() {
		return beginPeriod;
	}
	public void setBeginPeriod(int beginPeriod) {
		this.beginPeriod = beginPeriod;
	}
	public int getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(int endPeriod) {
		this.endPeriod = endPeriod;
	}
	public String getApplyComment() {
		return applyComment;
	}
	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}
	public Date getApplyEffectDate() {
		return applyEffectDate;
	}
	public void setApplyEffectDate(Date applyEffectDate) {
		this.applyEffectDate = applyEffectDate;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	
}
