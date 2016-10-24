package com.pujjr.carcredit.domain;

public class CallBackResult {
    private String id;

	private String runPathId;

	private String taskBusinessId;

	private Boolean isSelfListen;

	private Boolean isSelfSign;

	private String callbackResult;

	private Boolean isSelfAuth;

	private Boolean isKnowFinanceCarInfo;

	private Boolean isKnowFinanceAmtInfo;

	private String comment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRunPathId() {
		return runPathId;
	}

	public void setRunPathId(String runPathId) {
		this.runPathId = runPathId;
	}

	public String getTaskBusinessId() {
		return taskBusinessId;
	}

	public void setTaskBusinessId(String taskBusinessId) {
		this.taskBusinessId = taskBusinessId;
	}

	public Boolean getIsSelfListen() {
		return isSelfListen;
	}

	public void setIsSelfListen(Boolean isSelfListen) {
		this.isSelfListen = isSelfListen;
	}

	public Boolean getIsSelfSign() {
		return isSelfSign;
	}

	public void setIsSelfSign(Boolean isSelfSign) {
		this.isSelfSign = isSelfSign;
	}

	public String getCallbackResult() {
		return callbackResult;
	}

	public void setCallbackResult(String callbackResult) {
		this.callbackResult = callbackResult;
	}

	public Boolean getIsSelfAuth() {
		return isSelfAuth;
	}

	public void setIsSelfAuth(Boolean isSelfAuth) {
		this.isSelfAuth = isSelfAuth;
	}

	public Boolean getIsKnowFinanceCarInfo() {
		return isKnowFinanceCarInfo;
	}

	public void setIsKnowFinanceCarInfo(Boolean isKnowFinanceCarInfo) {
		this.isKnowFinanceCarInfo = isKnowFinanceCarInfo;
	}

	public Boolean getIsKnowFinanceAmtInfo() {
		return isKnowFinanceAmtInfo;
	}

	public void setIsKnowFinanceAmtInfo(Boolean isKnowFinanceAmtInfo) {
		this.isKnowFinanceAmtInfo = isKnowFinanceAmtInfo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}