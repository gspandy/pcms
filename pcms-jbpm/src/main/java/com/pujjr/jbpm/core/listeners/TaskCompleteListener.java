package com.pujjr.jbpm.core.listeners;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityWithVariablesEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.jbpm.core.handle.ITaskAssigneeHandle;
import com.pujjr.jbpm.core.handle.ITaskCompleteHandle;
import com.pujjr.jbpm.core.script.GroovyEngine;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;
import com.pujjr.utils.SpringBeanUtils;

public class TaskCompleteListener implements EventHandler {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	
	public void handle(ActivitiEvent event) 
	{
		// TODO Auto-generated method stub
		System.out.println("taskCreate completed");
		TaskEntity taskEntity = null;
		if(event instanceof ActivitiEntityWithVariablesEventImpl)
		{
			taskEntity = (TaskEntity) ((ActivitiEntityWithVariablesEventImpl)event).getEntity();
		}
		else
		{
			taskEntity = (TaskEntity) ((ActivitiEntityEventImpl)event).getEntity();
		}
		//执行任务完成脚本
		String workflowVersionId = runtimeService.getVariable(taskEntity.getExecutionId(), "workflowVersionId").toString();
		WorkflowNodeParamVo nodeParam = configWorkflowService.getWorkflowNodeParam(workflowVersionId,taskEntity.getTaskDefinitionKey());
		if(nodeParam!=null && nodeParam.getTaskCompleteHandle()!=null && !nodeParam.getTaskCompleteHandle().equals(""))
		{
			ITaskCompleteHandle handle = (ITaskCompleteHandle)SpringBeanUtils.getBean(nodeParam.getTaskCompleteHandle());
			handle.handle(taskEntity);
		}
		if (nodeParam!= null && nodeParam.getTaskCompleteScript() != null && !nodeParam.getTaskCompleteScript().equals("")) 
		{
			Map<String, Object> vars = runtimeService.getVariables(taskEntity.getExecutionId());
			GroovyEngine.execScript(nodeParam.getTaskCompleteScript(),taskEntity.getExecutionId(),vars);
		}
		
	}

}
