package com.pujjr.jbpm.domain;

public class WorkflowNodeAssignee {
    private String id;

    private String workflowVersionId;

    private String nodeId;

    private String assigneeType;

    private String assigneeParam;

    private String assigneeHandle;

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

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getAssigneeType() {
        return assigneeType;
    }

    public void setAssigneeType(String assigneeType) {
        this.assigneeType = assigneeType;
    }

    public String getAssigneeParam() {
        return assigneeParam;
    }

    public void setAssigneeParam(String assigneeParam) {
        this.assigneeParam = assigneeParam;
    }

    public String getAssigneeHandle() {
        return assigneeHandle;
    }

    public void setAssigneeHandle(String assigneeHandle) {
        this.assigneeHandle = assigneeHandle;
    }
}