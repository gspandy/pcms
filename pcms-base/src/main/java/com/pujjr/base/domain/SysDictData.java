package com.pujjr.base.domain;

public class SysDictData {
    private String id;

	private String dictTypeId;

	private Integer showSeq;

	private String dictDataCode;

	private String dictDataName;

	private Boolean enabled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public Integer getShowSeq() {
		return showSeq;
	}

	public void setShowSeq(Integer showSeq) {
		this.showSeq = showSeq;
	}

	public String getDictDataCode() {
		return dictDataCode;
	}

	public void setDictDataCode(String dictDataCode) {
		this.dictDataCode = dictDataCode;
	}

	public String getDictDataName() {
		return dictDataName;
	}

	public void setDictDataName(String dictDataName) {
		this.dictDataName = dictDataName;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}