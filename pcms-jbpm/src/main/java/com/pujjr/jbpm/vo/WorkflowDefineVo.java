package com.pujjr.jbpm.vo;

import java.util.Date;

public class WorkflowDefineVo 
{
	private String id;

    private String workflowTypeId;

    private String workflowName;

    private String workflowKey;

    private String workflowDesc;

    private Date createTime;

    private String createId;

    private Date updateTime;

    private String updateId;
    
    private String activateVersionId;

    private String activitiModelId;

    private String activitiDeployId;

    private String activitiProcdefId;

    private Integer version;

    private String versionStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkflowTypeId() {
		return workflowTypeId;
	}

	public void setWorkflowTypeId(String workflowTypeId) {
		this.workflowTypeId = workflowTypeId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getWorkflowKey() {
		return workflowKey;
	}

	public void setWorkflowKey(String workflowKey) {
		this.workflowKey = workflowKey;
	}

	public String getWorkflowDesc() {
		return workflowDesc;
	}

	public void setWorkflowDesc(String workflowDesc) {
		this.workflowDesc = workflowDesc;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getActivateVersionId() {
		return activateVersionId;
	}

	public void setActivateVersionId(String activateVersionId) {
		this.activateVersionId = activateVersionId;
	}

	public String getActivitiModelId() {
		return activitiModelId;
	}

	public void setActivitiModelId(String activitiModelId) {
		this.activitiModelId = activitiModelId;
	}

	public String getActivitiDeployId() {
		return activitiDeployId;
	}

	public void setActivitiDeployId(String activitiDeployId) {
		this.activitiDeployId = activitiDeployId;
	}

	public String getActivitiProcdefId() {
		return activitiProcdefId;
	}

	public void setActivitiProcdefId(String activitiProcdefId) {
		this.activitiProcdefId = activitiProcdefId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(String versionStatus) {
		this.versionStatus = versionStatus;
	}
    
    
    
    

}
