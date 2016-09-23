package com.pujjr.jbpm.vo;

public class WorkflowNodeAssigneeVo extends WorkflowNodeVo 
{
	private String assigneeType;

    private String assigneeParam;

    private String assigneeHandle;

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
