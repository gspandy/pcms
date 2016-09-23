package com.pujjr.jbpm.vo;


import java.util.Date;


public class WorkflowNodeFormVo extends WorkflowNodeVo
{
	private String formName;
	
	private Date createTime;
	
	private String createId;
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
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
	
	
	
	
}
