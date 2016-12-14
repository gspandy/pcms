package com.pujjr.base.domain;

import java.util.Date;

public class ArchiveBorrow {
    private String id;

	private String appId;

	private String borrowId;

	private String borrowDept;

	private String borrowReason;

	private Date borrowStartDate;

	private Date borrowEndDate;

	private String borrowComment;

	private Date returnDate;

	private Boolean isTimeout;

	private String timeoutReason;

	private String returnComment;

	private String approveResult;

	private String approveComment;

	private String createId;

	private Date createTime;

	private String procInstId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getBorrowDept() {
		return borrowDept;
	}

	public void setBorrowDept(String borrowDept) {
		this.borrowDept = borrowDept;
	}

	public String getBorrowReason() {
		return borrowReason;
	}

	public void setBorrowReason(String borrowReason) {
		this.borrowReason = borrowReason;
	}

	public Date getBorrowStartDate() {
		return borrowStartDate;
	}

	public void setBorrowStartDate(Date borrowStartDate) {
		this.borrowStartDate = borrowStartDate;
	}

	public Date getBorrowEndDate() {
		return borrowEndDate;
	}

	public void setBorrowEndDate(Date borrowEndDate) {
		this.borrowEndDate = borrowEndDate;
	}

	public String getBorrowComment() {
		return borrowComment;
	}

	public void setBorrowComment(String borrowComment) {
		this.borrowComment = borrowComment;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Boolean getIsTimeout() {
		return isTimeout;
	}

	public void setIsTimeout(Boolean isTimeout) {
		this.isTimeout = isTimeout;
	}

	public String getTimeoutReason() {
		return timeoutReason;
	}

	public void setTimeoutReason(String timeoutReason) {
		this.timeoutReason = timeoutReason;
	}

	public String getReturnComment() {
		return returnComment;
	}

	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}

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

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
}