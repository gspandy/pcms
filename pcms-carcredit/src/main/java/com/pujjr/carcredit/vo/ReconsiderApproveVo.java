package com.pujjr.carcredit.vo;

import com.pujjr.carcredit.domain.Reconsider;

public class ReconsiderApproveVo extends Reconsider 
{
	private String approveResult;
	private String approveComment;
	
	public String getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	public String getApproveComment() {
		return approveComment;
	}
	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}
	
	
}
