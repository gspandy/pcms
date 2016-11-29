package com.pujjr.jbpm.core.listeners;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.jbpm.core.script.GroovyEngine;
import com.pujjr.jbpm.dao.WorkflowGlobalParamMapper;
import com.pujjr.jbpm.domain.WorkflowGlobalParam;
import com.pujjr.jbpm.service.IConfigWorkflowService;

public class ProcessCancelledListener implements EventHandler 
{
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	
	public void handle(ActivitiEvent event) 
	{
		// TODO Auto-generated method stub
		ActivitiProcessCancelledEventImpl  eventImpl = ( ActivitiProcessCancelledEventImpl)event;	
		//执行流程取消脚本
		String workflowVersionId = runtimeService.getVariable(eventImpl.getExecutionId(), "workflowVersionId").toString();
		WorkflowGlobalParam globalParam = configWorkflowService.getGlobalParamByVersionId(workflowVersionId);
		if(globalParam !=null &&(globalParam.getWorkflowCancelScript()!=null && !globalParam.getWorkflowCancelScript().equals("")))
		{
			Map<String,Object> vars=runtimeService.getVariables(eventImpl.getExecutionId());  
			GroovyEngine.execScript(globalParam.getWorkflowStartScript(), eventImpl.getExecutionId(),vars);
		}
	}
}
