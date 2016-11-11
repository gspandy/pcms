package com.pujjr.postloan.vo;

import java.util.Date;
/**
 * 减免申请要素
 * **/
public class ApplyRemissionVo 
{
	//申请备注
	private String applyComment;
	//申请日期
	private Date applyDate;
	//减免日期
	private Date remissionDate;
	//减免项
	private RemissionFeeItemVo feeItemVo;
	
	public RemissionFeeItemVo getFeeItemVo() {
		return feeItemVo;
	}
	public void setFeeItemVo(RemissionFeeItemVo feeItemVo) {
		this.feeItemVo = feeItemVo;
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
	public Date getRemissionDate() {
		return remissionDate;
	}
	public void setRemissionDate(Date remissionDate) {
		this.remissionDate = remissionDate;
	}
}
