package com.pujjr.carcredit.vo;

import java.util.List;

public class TaskAssigneeVo 
{
	private List<ToDoTaskVo> toDoTaskList;
	
	private List<String> assingees;

	public List<ToDoTaskVo> getToDoTaskList() {
		return toDoTaskList;
	}

	public void setToDoTaskList(List<ToDoTaskVo> toDoTaskList) {
		this.toDoTaskList = toDoTaskList;
	}

	public List<String> getAssingees() {
		return assingees;
	}

	public void setAssingees(List<String> assingees) {
		this.assingees = assingees;
	}
}
