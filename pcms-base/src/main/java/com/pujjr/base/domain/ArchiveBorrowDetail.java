package com.pujjr.base.domain;

public class ArchiveBorrowDetail {
    private String id;

	private String borrowTaskId;

	private String archiveType;

	private String fileName;

	private String fileType;

	private Integer borrowCnt;

	private String comment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBorrowTaskId() {
		return borrowTaskId;
	}

	public void setBorrowTaskId(String borrowTaskId) {
		this.borrowTaskId = borrowTaskId;
	}

	public String getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(String archiveType) {
		this.archiveType = archiveType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getBorrowCnt() {
		return borrowCnt;
	}

	public void setBorrowCnt(Integer borrowCnt) {
		this.borrowCnt = borrowCnt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	//资料中文名称
	private String fileNameDesc;

	public String getFileNameDesc() {
		return fileNameDesc;
	}

	public void setFileNameDesc(String fileNameDesc) {
		this.fileNameDesc = fileNameDesc;
	}
}