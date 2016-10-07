package com.pujjr.carcredit.vo;
/**
 * 复议值对象
 * **/
public class ReconsiderApplyVo 
{
	private String rejectReason;
	private String recommitReason;
	private String recommitComment;
	
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getRecommitReason() {
		return recommitReason;
	}
	public void setRecommitReason(String recommitReason) {
		this.recommitReason = recommitReason;
	}
	public String getRecommitComment() {
		return recommitComment;
	}
	public void setRecommitComment(String recommitComment) {
		this.recommitComment = recommitComment;
	}
	
	
}
