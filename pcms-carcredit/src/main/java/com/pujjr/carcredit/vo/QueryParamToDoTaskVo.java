package com.pujjr.carcredit.vo;

import com.pujjr.base.vo.QueryParamPageVo;

/**
 * 代办任务查询条件
 * **/
public class QueryParamToDoTaskVo extends QueryParamPageVo
{
	//包含的任务标识
	private String inTaskDefKeys;
	//不包含任务标识
	private String outTaskDefKeys;
	//申请单号
	private String appId;
	//客户姓名
	private String name;
	//申请单状态
	private String appStatus;
	
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getInTaskDefKeys() {
		return inTaskDefKeys;
	}
	public void setInTaskDefKeys(String inTaskDefKeys) {
		this.inTaskDefKeys = inTaskDefKeys;
	}
	public String getOutTaskDefKeys() {
		return outTaskDefKeys;
	}
	public void setOutTaskDefKeys(String outTaskDefKeys) {
		this.outTaskDefKeys = outTaskDefKeys;
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
	
}
