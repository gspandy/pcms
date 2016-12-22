package com.pujjr.assetsmanage.po;

import com.pujjr.base.domain.ArchiveDetail;

public class ArchiveDetailPo extends ArchiveDetail 
{
	//档案资料名称中文描述
	private String fileNameDesc;

	public String getFileNameDesc() {
		return fileNameDesc;
	}

	public void setFileNameDesc(String fileNameDesc) {
		this.fileNameDesc = fileNameDesc;
	}
	
}
