package com.pujjr.jbpm.core.listeners;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.pujjr.jbpm.core.command.AbstractExecutionCommand;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.core.command.ProcessNextCommand;
import com.pujjr.jbpm.core.exception.NoAssigneeException;
import com.pujjr.jbpm.core.handle.ITaskAssigneeHandle;
import com.pujjr.jbpm.core.ProcessHandleHelper;
import com.pujjr.jbpm.domain.WorkflowNodeAssignee;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.core.script.GroovyEngine;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;
import com.pujjr.utils.SpringBeanUtils;
import com.pujjr.jbpm.vo.AssigneeType;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;

public class TaskCreateListener implements EventHandler 
{
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	
	public void handle(ActivitiEvent event)
	{
		// TODO Auto-generated method stub
		TaskEntity taskEntity = (TaskEntity) ((ActivitiEntityEventImpl)event).getEntity();
		//执行任务创建脚本
		String workflowVersionId = runtimeService.getVariable(taskEntity.getExecutionId(), "workflowVersionId").toString();
		WorkflowNodeParamVo nodeParam = configWorkflowService.getWorkflowNodeParam(workflowVersionId,taskEntity.getTaskDefinitionKey());
		if (nodeParam!= null && nodeParam.getTaskCreateScript()!= null && nodeParam.getTaskCreateScript() != "")
		{
			Map<String, Object> vars = runtimeService.getVariables(taskEntity.getExecutionId());
			GroovyEngine.execScript(nodeParam.getTaskCreateScript(),taskEntity.getExecutionId(), vars);
		}
		//更新历史路径信息
		AbstractExecutionCommand cmd = ProcessHandleHelper.getProcessCommand();
		WorkflowRunPath runPath = runPathService.getFarestRunPathByActId(taskEntity.getProcessInstanceId(), taskEntity.getTaskDefinitionKey());
		if(cmd instanceof ProcessNextCommand)
		{
			ProcessNextCommand nextCommand = (ProcessNextCommand) cmd;
			//如果是回退操作，则回退到的任务节点应由原执行人操作
			if(nextCommand.getCommandType().equals(CommandType.BACK)||nextCommand.getCommandType().equals(CommandType.BACT_TO_STARTER))
			{
				WorkflowRunPath refRunPath = runPathService.getRunPathById(runPath.getRefPathId());
				if(refRunPath != null)
				{
					taskEntity.setAssignee(refRunPath.getAssignee());
					publishAssignEvent(taskEntity);
					runPath.setAssignee(refRunPath.getAssignee());
				}
				else
				{
					taskEntity.setAssignee("V0002");
					publishAssignEvent(taskEntity);
					runPath.setAssignee("V0002");
				}
				
			}
			else
			{
				//如果是流程往前走则获取节点审批人参数,根据参数获取节点审批人
				String assignee = getTaskAssignee(workflowVersionId,taskEntity);
				taskEntity.setAssignee(assignee);
				publishAssignEvent(taskEntity);
				runPath.setAssignee(assignee);
			}
		}
		else
		{
			String assignee = getTaskAssignee(workflowVersionId,taskEntity);
			taskEntity.setAssignee(assignee);
			publishAssignEvent(taskEntity);
			runPath.setAssignee(assignee);
		}
		runPathService.updateWorkflowRunPath(runPath);

	}
	private String getTaskAssignee(String workflowVersionId,TaskEntity taskEntity)
	{
		WorkflowNodeAssignee nodeAssigneeParam = configWorkflowService.getNodeAssignee(workflowVersionId, taskEntity.getTaskDefinitionKey());
		String assignee = null;
		String assigneeType = nodeAssigneeParam.getAssigneeType();
		if(assigneeType.equals(AssigneeType.FROM_VARIABLE))
		{
			assignee = taskEntity.getVariable(nodeAssigneeParam.getAssigneeParam()).toString();
		}
		else if(assigneeType.equals(AssigneeType.PROCESS_OWNER))
		{
			assignee = taskEntity.getVariable(ProcessGlobalVariable.WORKFLOW_OWNER).toString();
		}
		else if(assigneeType.equals(AssigneeType.ASSIGNEE_ACCOUNT))
		{
			assignee = nodeAssigneeParam.getAssigneeParam();
		}
		else if(assigneeType.equals(AssigneeType.ASSIGNEE_WORKGROUP))
		{
			ITaskAssigneeHandle handler = (ITaskAssigneeHandle)SpringBeanUtils.getBean(nodeAssigneeParam.getAssigneeHandle());
			assignee = handler.handle(nodeAssigneeParam.getAssigneeParam(),taskEntity);
		}
		else
		{
			assignee = taskEntity.getAssignee();
		}
		
		return assignee;
	}
	
	 /** 
	  * 发布任务分配事件 
	  * @param taskEntity 
	  */  
	 private void publishAssignEvent(TaskEntity taskEntity)
	 {  
	   if (!StringUtils.isEmpty(taskEntity.getAssignee())) 
	   {  
	       Context.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(  
	               ActivitiEventBuilder.createEntityEvent(ActivitiEventType.TASK_ASSIGNED, taskEntity));  
	    }  
	 }  

}
