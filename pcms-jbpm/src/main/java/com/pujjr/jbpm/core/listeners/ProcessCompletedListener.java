package com.pujjr.jbpm.core.listeners;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.jbpm.core.script.GroovyEngine;
import com.pujjr.jbpm.domain.WorkflowGlobalParam;
import com.pujjr.jbpm.service.IConfigWorkflowService;

public class ProcessCompletedListener implements EventHandler {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	
	public void handle(ActivitiEvent event) 
	{
		// TODO Auto-generated method stub
		 ActivitiEntityEventImpl  eventImpl = (  ActivitiEntityEventImpl)event;	
		//ִ��������ɽű�
		String workflowVersionId = runtimeService.getVariable(eventImpl.getExecutionId(), "workflowVersionId").toString();
		WorkflowGlobalParam globalParam = configWorkflowService.getGlobalParamByVersionId(workflowVersionId);
		if(globalParam !=null &&(globalParam.getWorkflowCompleteScript()!=null && !globalParam.getWorkflowCompleteScript().equals("")))
		{
			Map<String,Object> vars=runtimeService.getVariables(eventImpl.getExecutionId());  
			GroovyEngine.execScript(globalParam.getWorkflowCompleteScript(),eventImpl.getExecutionId(), vars);
		}
	}

}
