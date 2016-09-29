package com.pujjr.carcredit.domain;

import java.util.Date;

public class HisOper {
    public String id;

	public String appId;

	public String tableName;

	public String fieldName;

	public String className;

	public String updMode;

	public String preValue;

	public String afterValue;

	public Date operTime;

	public String accounId;

	public String recordId;

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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getUpdMode() {
		return updMode;
	}

	public void setUpdMode(String updMode) {
		this.updMode = updMode;
	}

	public String getPreValue() {
		return preValue;
	}

	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}

	public String getAfterValue() {
		return afterValue;
	}

	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getAccounId() {
		return accounId;
	}

	public void setAccounId(String accounId) {
		this.accounId = accounId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	
}