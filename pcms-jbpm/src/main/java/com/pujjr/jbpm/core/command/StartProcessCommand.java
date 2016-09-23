package com.pujjr.jbpm.core.command;

public class StartProcessCommand extends AbstractExecutionCommand 
{
	//流程定义标识
	private String processDefinitionKey;
	//业务主键
	private String businessKey;

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	
	
	
}
