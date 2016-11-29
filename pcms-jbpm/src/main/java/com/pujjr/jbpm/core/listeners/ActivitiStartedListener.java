package com.pujjr.jbpm.core.listeners;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.context.ExecutionContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.jbpm.core.command.AbstractExecutionCommand;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.core.command.ProcessNextCommand;
import com.pujjr.jbpm.core.ProcessHandleHelper;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.core.script.GroovyEngine;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;
import com.pujjr.utils.Utils;

public class ActivitiStartedListener implements EventHandler 
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
		AbstractExecutionCommand cmd = ProcessHandleHelper.getProcessCommand();
		ActivitiActivityEventImpl  eventImpl = ( ActivitiActivityEventImpl) event;
		ExecutionContext executionContext = Context.getExecutionContext();
		ExecutionEntity executionEntity = executionContext.getExecution();
		
		//执行活动启动脚本
		String workflowVersionId = runtimeService.getVariable(eventImpl.getExecutionId(), "workflowVersionId").toString();
		WorkflowNodeParamVo nodeParam = configWorkflowService.getWorkflowNodeParam(workflowVersionId, eventImpl.getActivityId());
		if(nodeParam!= null && nodeParam.getActStartScript()!=null && !nodeParam.getActStartScript().equals(""))
		{
			Map<String,Object> vars=runtimeService.getVariables(eventImpl.getExecutionId());  
			GroovyEngine.execScript(nodeParam.getActStartScript(),eventImpl.getExecutionId(),vars);
		}
		
		ActivityImpl curAct = executionEntity.getActivity();
		
		//设置路径信息
		WorkflowRunPath path = new WorkflowRunPath();
		path.setId(Utils.get16UUID());
		path.setProcDefId(eventImpl.getProcessDefinitionId());
		path.setProcInstId(eventImpl.getProcessInstanceId());
		path.setExecutionId(eventImpl.getExecutionId());
		path.setActId(eventImpl.getActivityId());
		String actName = eventImpl.getActivityName();
		String actType = eventImpl.getActivityType();
		if(actName == null)
		{
			actName = actType;
		}
		path.setActName(actName);
		path.setActType(eventImpl.getActivityType());
		path.setIsMultiAct(false);
		path.setStartTime(new Date());
		path.setInJumpType(cmd.getCommandType().toString());
		
		//开始节点则设置父节点为0
		if(curAct.getActivityBehavior() instanceof  NoneStartEventActivityBehavior)
		{
			path.setNodeLevel(1);
			path.setParentPathId("0");
		}
		else
		{
			//获取父节点路径
			WorkflowRunPath parentNode = runPathService.getFarestRunPathByActId(eventImpl.getProcessInstanceId(), cmd.getActId());
			path.setNodeLevel(parentNode.getNodeLevel()+1);
			path.setParentPathId(parentNode.getId());
			if(cmd instanceof ProcessNextCommand)
			{
				ProcessNextCommand nextCmd = (ProcessNextCommand)cmd;
				if(nextCmd.getCommandType().equals(CommandType.BACK) || nextCmd.getCommandType().equals(CommandType.BACT_TO_STARTER))
				{
					try
					{
						WorkflowRunPath backNodeRunPath = ProcessHandleHelper.getBackNode();
						path.setRefPathId(backNodeRunPath.getId());
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}
				path.setParentUsertaskPathId(nextCmd.getCurRunPathId());
			}
			
		}
		
		runPathService.createWorkflowRunPath(path);
	}

}
