package com.pujjr.base.domain;

public class ArchiveDetail {
    private String id;

	private String archiveTaskId;

	private String fileName;

	private Integer postFileCnt;

	private Integer recvFileCnt;

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

	public Integer getPostFileCnt() {
		return postFileCnt;
	}

	public void setPostFileCnt(Integer postFileCnt) {
		this.postFileCnt = postFileCnt;
	}

	public Integer getRecvFileCnt() {
		return recvFileCnt;
	}

	public void setRecvFileCnt(Integer recvFileCnt) {
		this.recvFileCnt = recvFileCnt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}