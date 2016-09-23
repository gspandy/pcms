package com.pujjr.jbpm.domain;

import java.util.Date;

public class WorkflowRunPath {
    private String id;

    private String procDefId;

    private String procInstId;

    private String executionId;

    private String actId;

    private String actName;

    private String actType;

    private Boolean isMultiAct;

    private String assignee;

    private String agent;

    private String message;

    private Integer nodeLevel;

    private Date startTime;

    private Date endTime;

    private Integer durationTime;

    private Integer effectTime;

    private String inJumpType;

    private String outJumpType;

    private String refPathId;

    private String parentPathId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public Boolean getIsMultiAct() {
        return isMultiAct;
    }

    public void setIsMultiAct(Boolean isMultiAct) {
        this.isMultiAct = isMultiAct;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public Integer getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Integer effectTime) {
        this.effectTime = effectTime;
    }

    public String getInJumpType() {
        return inJumpType;
    }

    public void setInJumpType(String inJumpType) {
        this.inJumpType = inJumpType;
    }

    public String getOutJumpType() {
        return outJumpType;
    }

    public void setOutJumpType(String outJumpType) {
        this.outJumpType = outJumpType;
    }

    public String getRefPathId() {
        return refPathId;
    }

    public void setRefPathId(String refPathId) {
        this.refPathId = refPathId;
    }

    public String getParentPathId() {
        return parentPathId;
    }

    public void setParentPathId(String parentPathId) {
        this.parentPathId = parentPathId;
    }
}