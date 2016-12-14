package com.pujjr.base.domain;

public class ArchiveStore {
    private String id;

    private String appId;

    private String archiveType;

    private String fileName;

    private Integer totalFileCnt;

    private Integer remainFileCnt;

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

    public Integer getTotalFileCnt() {
        return totalFileCnt;
    }

    public void setTotalFileCnt(Integer totalFileCnt) {
        this.totalFileCnt = totalFileCnt;
    }

    public Integer getRemainFileCnt() {
        return remainFileCnt;
    }

    public void setRemainFileCnt(Integer remainFileCnt) {
        this.remainFileCnt = remainFileCnt;
    }
    
    //档案类型中文描述
    private String archiveTypeDesc;
    //文件名称中文描述
    private String fileNameDesc;

	public String getArchiveTypeDesc() {
		return archiveTypeDesc;
	}

	public void setArchiveTypeDesc(String archiveTypeDesc) {
		this.archiveTypeDesc = archiveTypeDesc;
	}

	public String getFileNameDesc() {
		return fileNameDesc;
	}

	public void setFileNameDesc(String fileNameDesc) {
		this.fileNameDesc = fileNameDesc;
	}
}