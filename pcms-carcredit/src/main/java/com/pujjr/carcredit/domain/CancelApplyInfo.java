package com.pujjr.carcredit.domain;

public class CancelApplyInfo {
    private String id;

	private String runPathId;

	private String taskBusinessId;

	private String cancelResult;

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

	public String getCancelResult() {
		return cancelResult;
	}

	public void setCancelResult(String cancelResult) {
		this.cancelResult = cancelResult;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}