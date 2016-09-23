package com.pujjr.jbpm.domain;

import java.util.Date;

public class WorkflowGateWayParam {
    private String id;

	private String workflowVersionId;

	private String nodeId;

	private String outNodeId;

	private String outScript;

	private Date createTime;

	private String createId;

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

	public String getOutNodeId() {
		return outNodeId;
	}

	public void setOutNodeId(String outNodeId) {
		this.outNodeId = outNodeId;
	}

	public String getOutScript() {
		return outScript;
	}

	public void setOutScript(String outScript) {
		this.outScript = outScript;
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