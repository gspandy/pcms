package com.pujjr.jbpm.core.command;

public class StartProcessCommand extends AbstractExecutionCommand 
{
	//���̶����ʶ
	private String processDefinitionKey;
	//ҵ������
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
