package com.pujjr.carcredit.vo;

public class ApplyCheckVo extends TaskCommitVo
{
	private String netCheckResult;

    private String netCheckNotPassReason;

    private String netCheckComment;

    private String telCheckResult;

    private String telCheckNotPassReason;

    private String telCheckComment;

	public String getNetCheckResult() {
		return netCheckResult;
	}

	public void setNetCheckResult(String netCheckResult) {
		this.netCheckResult = netCheckResult;
	}

	public String getNetCheckNotPassReason() {
		return netCheckNotPassReason;
	}

	public void setNetCheckNotPassReason(String netCheckNotPassReason) {
		this.netCheckNotPassReason = netCheckNotPassReason;
	}

	public String getNetCheckComment() {
		return netCheckComment;
	}

	public void setNetCheckComment(String netCheckComment) {
		this.netCheckComment = netCheckComment;
	}

	public String getTelCheckResult() {
		return telCheckResult;
	}

	public void setTelCheckResult(String telCheckResult) {
		this.telCheckResult = telCheckResult;
	}

	public String getTelCheckNotPassReason() {
		return telCheckNotPassReason;
	}

	public void setTelCheckNotPassReason(String telCheckNotPassReason) {
		this.telCheckNotPassReason = telCheckNotPassReason;
	}

	public String getTelCheckComment() {
		return telCheckComment;
	}

	public void setTelCheckComment(String telCheckComment) {
		this.telCheckComment = telCheckComment;
	}
    
    
}
