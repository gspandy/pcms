package com.pujjr.jbpm.core.command;

public class ProcessNextCommand extends AbstractExecutionCommand
{
	//任务ID
	private String taskId;
	//任务执行者
	private String assignee; 
	//任务目的节点
	private String destNodeId;
	//当前路径ID
	private String curRunPathId;
	
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

	public String getCurRunPathId() {
		return curRunPathId;
	}

	public void setCurRunPathId(String curRunPathId) {
		this.curRunPathId = curRunPathId;
	}
	
}
