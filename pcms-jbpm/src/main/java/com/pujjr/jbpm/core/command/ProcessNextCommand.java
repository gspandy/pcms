package com.pujjr.jbpm.core.command;

public class ProcessNextCommand extends AbstractExecutionCommand
{
	//����ID
	private String taskId;
	//����ִ����
	private String assignee; 
	//����Ŀ�Ľڵ�
	private String destNodeId;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDestNodeId() {
		return destNodeId;
	}

	public void setDestNodeId(String destNodeId) {
		this.destNodeId = destNodeId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
}
