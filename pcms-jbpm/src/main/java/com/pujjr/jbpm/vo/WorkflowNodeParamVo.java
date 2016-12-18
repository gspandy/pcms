package com.pujjr.jbpm.vo;

import java.util.Date;


public class WorkflowNodeParamVo extends WorkflowNodeVo 
{
	private String actStartScript;

	private String actEndScript;

	private String taskCreatePrehandle;

	private String taskCreateAfterhandle;
	
	private String taskCompleteHandle;

	private String taskCreateScript;

	private String taskCompleteScript;

	private Boolean onSiteNotice;

	private Boolean onMailNotice;

	private Boolean onMsgNotice;

	private Boolean onWeixinNotice;
	
	private String recommitMode;
	
	private String backNodeId;
	
	private boolean isMulti;
	
	private String multiPassMode;

	private String multiPassCond;

	private Date createTime;

	private String createId;

	public String getMultiPassMode() {
		return multiPassMode;
	}

	public void setMultiPassMode(String multiPassMode) {
		this.multiPassMode = multiPassMode;
	}

	public String getMultiPassCond() {
		return multiPassCond;
	}

	public void setMultiPassCond(String multiPassCond) {
		this.multiPassCond = multiPassCond;
	}

	public String getActStartScript() {
		return actStartScript;
	}

	public void setActStartScript(String actStartScript) {
		this.actStartScript = actStartScript;
	}

	public String getActEndScript() {
		return actEndScript;
	}

	public void setActEndScript(String actEndScript) {
		this.actEndScript = actEndScript;
	}

	public String getTaskCreatePrehandle() {
		return taskCreatePrehandle;
	}

	public void setTaskCreatePrehandle(String taskCreatePrehandle) {
		this.taskCreatePrehandle = taskCreatePrehandle;
	}

	public String getTaskCreateAfterhandle() {
		return taskCreateAfterhandle;
	}

	public void setTaskCreateAfterhandle(String taskCreateAfterhandle) {
		this.taskCreateAfterhandle = taskCreateAfterhandle;
	}

	public String getTaskCreateScript() {
		return taskCreateScript;
	}

	public void setTaskCreateScript(String taskCreateScript) {
		this.taskCreateScript = taskCreateScript;
	}

	public String getTaskCompleteScript() {
		return taskCompleteScript;
	}

	public void setTaskCompleteScript(String taskCompleteScript) {
		this.taskCompleteScript = taskCompleteScript;
	}

	public Boolean getOnSiteNotice() {
		return onSiteNotice;
	}

	public void setOnSiteNotice(Boolean onSiteNotice) {
		this.onSiteNotice = onSiteNotice;
	}

	public Boolean getOnMailNotice() {
		return onMailNotice;
	}

	public void setOnMailNotice(Boolean onMailNotice) {
		this.onMailNotice = onMailNotice;
	}

	public Boolean getOnMsgNotice() {
		return onMsgNotice;
	}

	public void setOnMsgNotice(Boolean onMsgNotice) {
		this.onMsgNotice = onMsgNotice;
	}

	public Boolean getOnWeixinNotice() {
		return onWeixinNotice;
	}

	public void setOnWeixinNotice(Boolean onWeixinNotice) {
		this.onWeixinNotice = onWeixinNotice;
	}

	public String getRecommitMode() {
		return recommitMode;
	}

	public void setRecommitMode(String recommitMode) {
		this.recommitMode = recommitMode;
	}

	public String getBackNodeId() {
		return backNodeId;
	}

	public void setBackNodeId(String backNodeId) {
		this.backNodeId = backNodeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getTaskCompleteHandle() {
		return taskCompleteHandle;
	}

	public void setTaskCompleteHandle(String taskCompleteHandle) {
		this.taskCompleteHandle = taskCompleteHandle;
	}

	public boolean isMulti() {
		return isMulti;
	}

	public void setMulti(boolean isMulti) {
		this.isMulti = isMulti;
	}
	
}
