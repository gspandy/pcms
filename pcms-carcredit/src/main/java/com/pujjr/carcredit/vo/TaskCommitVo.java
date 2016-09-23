package com.pujjr.carcredit.vo;

public class TaskCommitVo 
{
	//处理结果
	private String result;
	//放款附加条件
	private String loanExtConditon;
	//拒绝原因
	private String rejectReason;
	//取消原因
	private String cancelReason;
	//备注
	private String comment;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getLoanExtConditon() {
		return loanExtConditon;
	}
	public void setLoanExtConditon(String loanExtConditon) {
		this.loanExtConditon = loanExtConditon;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
}
