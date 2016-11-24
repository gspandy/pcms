package com.pujjr.postloan.vo;

import java.util.List;

/**
 * 其他费用申请对象
 * **/
public class ApplyOtherFeeVo 
{
	//申请备注
	private String applyComment;
	//总金额
	private double feeTotalAmount;
	//明细列表
	private List<OtherFeeDetailVo> detailList;
	
	public double getFeeTotalAmount() {
		return feeTotalAmount;
	}
	public void setFeeTotalAmount(double feeTotalAmount) {
		this.feeTotalAmount = feeTotalAmount;
	}
	
	public String getApplyComment() {
		return applyComment;
	}
	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}
	public List<OtherFeeDetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<OtherFeeDetailVo> detailList) {
		this.detailList = detailList;
	}
	
}

