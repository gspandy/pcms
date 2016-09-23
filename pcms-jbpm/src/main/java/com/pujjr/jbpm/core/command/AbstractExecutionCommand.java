package com.pujjr.jbpm.core.command;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractExecutionCommand 
{
	//��ڵ�ID
	private String actId;
	//�������
	private String message;
	//��������
	private CommandType commandType;
	//�����������
	private Map<String,Object> vars =new HashMap<String,Object>();

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public Map<String, Object> getVars() {
		return vars;
	}

	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
	}

	
	
	
}
