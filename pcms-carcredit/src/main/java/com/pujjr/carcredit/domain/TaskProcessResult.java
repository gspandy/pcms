package com.pujjr.carcredit.domain;

public class TaskProcessResult {
    public String id;

    public String runPathId;

    public String processResult;

    public String processResultDesc;

    public String comment;

    public String taskBusinessId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRunPathId() {
        return runPathId;
    }

    public void setRunPathId(String runPathId) {
        this.runPathId = runPathId;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public String getProcessResultDesc() {
        return processResultDesc;
    }

    public void setProcessResultDesc(String processResultDesc) {
        this.processResultDesc = processResultDesc;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTaskBusinessId() {
        return taskBusinessId;
    }

    public void setTaskBusinessId(String taskBusinessId) {
        this.taskBusinessId = taskBusinessId;
    }
}