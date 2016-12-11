package com.pujjr.base.domain;

public class ArchiveDetail {
    private String id;

	private String archiveTaskId;

	private String fileName;

	private Integer fileCnt;

	private Boolean checkResult;

	private String comment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArchiveTaskId() {
		return archiveTaskId;
	}

	public void setArchiveTaskId(String archiveTaskId) {
		this.archiveTaskId = archiveTaskId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileCnt() {
		return fileCnt;
	}

	public void setFileCnt(Integer fileCnt) {
		this.fileCnt = fileCnt;
	}

	public Boolean getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Boolean checkResult) {
		this.checkResult = checkResult;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}