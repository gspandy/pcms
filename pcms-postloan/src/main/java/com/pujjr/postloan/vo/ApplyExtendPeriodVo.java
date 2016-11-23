package com.pujjr.postloan.vo;

import java.util.Date;
import java.util.List;

import com.pujjr.postloan.domain.RemissionItem;

/**
 * 申请展期要素
 * **/
public class ApplyExtendPeriodVo 
{
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
	//减免费用
	private RemissionItem remissionFeeItemVo;
	//申请有效截止日期
	private Date applyEndDate;
	
	public RemissionItem getRemissionFeeItemVo() {
		return remissionFeeItemVo;
	}
	public void setRemissionFeeItemVo(RemissionItem remissionFeeItemVo) {
		this.remissionFeeItemVo = remissionFeeItemVo;
	}
	public Date getApplyEndDate() {
		return applyEndDate;
	}
	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
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
