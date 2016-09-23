package com.pujjr.jbpm.domain;

public class WorkflowVersion {
    private String id;

    private String workflowDefineId;

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

    public String getWorkflowDefineId() {
        return workflowDefineId;
    }

    public void setWorkflowDefineId(String workflowDefineId) {
        this.workflowDefineId = workflowDefineId;
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