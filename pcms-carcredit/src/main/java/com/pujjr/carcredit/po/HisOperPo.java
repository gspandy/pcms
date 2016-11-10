package com.pujjr.carcredit.po;

import java.util.Date;

import com.pujjr.carcredit.domain.HisFieldComment;

public class HisOperPo {
    private String id;

	private String recordId;

	private String appId;

	private String tableName;

	private String fieldName;

	private String className;

	private String updMode;

	private String preValue;

	private String afterValue;

	private Date operTime;

	private String accountId;
	
	private String tableCname;
	
	private String fieldCname;
	

	public String getTableCname() {
		return tableCname;
	}

	public void setTableCname(String tableCname) {
		this.tableCname = tableCname;
	}

	public String getFieldCname() {
		return fieldCname;
	}

	public void setFieldCname(String fieldCname) {
		this.fieldCname = fieldCname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}