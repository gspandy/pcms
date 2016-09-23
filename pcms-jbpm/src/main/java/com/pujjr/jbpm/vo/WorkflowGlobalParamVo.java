package com.pujjr.jbpm.vo;

import java.util.Date;

public class WorkflowGlobalParamVo {
	private String id;

    private String workflowVersionId;

    private Boolean isJumpFirstTask;

    private String workflowCreatePrehandle;

    private String workflowCreateAfterhandle;

    private String workflowEndHandle;

    private String workflowStartScript;

    private String workflowCompleteScript;

    private String workflowCancelScript;

    private Date createTime;

    private String createId;

    private Date updateTime;

    private String updateId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkflowVersionId() {
        return workflowVersionId;
    }

    public void setWorkflowVersionId(String workflowVersionId) {
        this.workflowVersionId = workflowVersionId;
    }

    public Boolean getIsJumpFirstTask() {
        return isJumpFirstTask;
    }

    public void setIsJumpFirstTask(Boolean isJumpFirstTask) {
        this.isJumpFirstTask = isJumpFirstTask;
    }

    public String getWorkflowCreatePrehandle() {
        return workflowCreatePrehandle;
    }

    public void setWorkflowCreatePrehandle(String workflowCreatePrehandle) {
        this.workflowCreatePrehandle = workflowCreatePrehandle;
    }

    public String getWorkflowCreateAfterhandle() {
        return workflowCreateAfterhandle;
    }

    public void setWorkflowCreateAfterhandle(String workflowCreateAfterhandle) {
        this.workflowCreateAfterhandle = workflowCreateAfterhandle;
    }

    public String getWorkflowEndHandle() {
        return workflowEndHandle;
    }

    public void setWorkflowEndHandle(String workflowEndHandle) {
        this.workflowEndHandle = workflowEndHandle;
    }

    public String getWorkflowStartScript() {
        return workflowStartScript;
    }

    public void setWorkflowStartScript(String workflowStartScript) {
        this.workflowStartScript = workflowStartScript;
    }

    public String getWorkflowCompleteScript() {
        return workflowCompleteScript;
    }

    public void setWorkflowCompleteScript(String workflowCompleteScript) {
        this.workflowCompleteScript = workflowCompleteScript;
    }

    public String getWorkflowCancelScript() {
        return workflowCancelScript;
    }

    public void setWorkflowCancelScript(String workflowCancelScript) {
        this.workflowCancelScript = workflowCancelScript;
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

}
