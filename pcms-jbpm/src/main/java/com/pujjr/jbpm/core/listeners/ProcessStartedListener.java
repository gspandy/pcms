package com.pujjr.jbpm.core.listeners;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.jbpm.core.script.GroovyEngine;
import com.pujjr.jbpm.domain.WorkflowGlobalParam;
import com.pujjr.jbpm.service.IConfigWorkflowService;

public class ProcessStartedListener implements EventHandler {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	
	public void handle(ActivitiEvent event) {
		// TODO Auto-generated method stub
		ActivitiProcessStartedEventImpl  eventImpl = ( ActivitiProcessStartedEventImpl)event;	
		//执行流程启动脚本
		String workflowVersionId = runtimeService.getVariable(eventImpl.getExecutionId(), "workflowVersionId").toString();
		WorkflowGlobalParam globalParam = configWorkflowService.getGlobalParamByVersionId(workflowVersionId);
		if(globalParam !=null &&(globalParam.getWorkflowStartScript()!=null && globalParam.getWorkflowStartScript()!=""))
		{
			Map<String,Object> vars=runtimeService.getVariables(eventImpl.getExecutionId());  
			GroovyEngine.execScript(globalParam.getWorkflowStartScript(),eventImpl.getExecutionId(), vars);
		}
	}

}
