package com.pujjr.base.domain;

public class RuleCollectionTask {
    private String id;

    private String workgroupId;

    private String taskType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkgroupId() {
        return workgroupId;
    }

    public void setWorkgroupId(String workgroupId) {
        this.workgroupId = workgroupId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}