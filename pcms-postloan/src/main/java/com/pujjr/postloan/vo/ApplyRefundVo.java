package com.pujjr.postloan.vo;

import java.util.Date;
/**
 * 退款申请要素
 * **/
public class ApplyRefundVo 
{
	//申请备注
	private String applyComment;
	//申请日期
	private Date applyDate;
	//挂账金额
	private double stayAmount;
	//挂账时长
	private int stayDay;
	//退款金额
	private double refundAmount;
	//退款日期
	private Date refundDate;
	
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
	public double getStayAmount() {
		return stayAmount;
	}
	public void setStayAmount(double stayAmount) {
		this.stayAmount = stayAmount;
	}
	public int getStayDay() {
		return stayDay;
	}
	public void setStayDay(int stayDay) {
		this.stayDay = stayDay;
	}
	public double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	
	
}
