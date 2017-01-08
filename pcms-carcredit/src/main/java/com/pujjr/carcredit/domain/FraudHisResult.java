package com.pujjr.carcredit.domain;

public class FraudHisResult {
    private String id;

	private String taskNodeName;

	private Integer seq;

	private String appId;

	private String name;

	private Boolean isBlack;

	private String newFieldName;

	private String newFieldValue;

	private String oldAppId;

	private String oldFieldName;

	private String oldFieldValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskNodeName() {
		return taskNodeName;
	}

	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Boolean isBlack) {
		this.isBlack = isBlack;
	}

	public String getNewFieldName() {
		return newFieldName;
	}

	public void setNewFieldName(String newFieldName) {
		this.newFieldName = newFieldName;
	}

	public String getNewFieldValue() {
		return newFieldValue;
	}

	public void setNewFieldValue(String newFieldValue) {
		this.newFieldValue = newFieldValue;
	}

	public String getOldAppId() {
		return oldAppId;
	}

	public void setOldAppId(String oldAppId) {
		this.oldAppId = oldAppId;
	}

	public String getOldFieldName() {
		return oldFieldName;
	}

	public void setOldFieldName(String oldFieldName) {
		this.oldFieldName = oldFieldName;
	}

	public String getOldFieldValue() {
		return oldFieldValue;
	}

	public void setOldFieldValue(String oldFieldValue) {
		this.oldFieldValue = oldFieldValue;
	}
}