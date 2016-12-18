package com.pujjr.jbpm.core.listeners;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.jbpm.core.ProcessHandleHelper;
import com.pujjr.jbpm.domain.WorkflowNodeAssignee;
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
	@Autowired
	private ISysWorkgroupService workgroupService;
	
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
		if(nodeParam.isMulti())
		{
			WorkflowRunPath runPath = runPathService.getFarestRunPathByActId(eventImpl.getProcessInstanceId(), eventImpl.getActivityId());
			if(runPath != null)
			{
				List<String> list =(List<String>) runtimeService.getVariable(executionEntity.getId(), "assigneeList");
				String userIds = "";
				for(String item : list)
				{
					userIds+= item +",";
				}
				if(userIds.length()>0)
				{
					userIds = userIds.substring(0, userIds.length()-1);
				}
				runPath.setAssignee(userIds);
				runPathService.updateWorkflowRunPath(runPath);
				return;
			}
		}
		
		//设置路径信息
		WorkflowRunPath path = new WorkflowRunPath();
		path.setId(Utils.get16UUID());
		path.setProcDefId(eventImpl.getProcessDefinitionId());
		path.setProcInstId(eventImpl.getProcessInstanceId());
		path.setExecutionId(eventImpl.getExecutionId());
		path.setActId(eventImpl.getActivityId());
		path.setIsMultiAct(nodeParam.isMulti());
		String actName = eventImpl.getActivityName();
		String actType = eventImpl.getActivityType();
		if(actName == null)
		{
			actName = actType;
		}
		path.setActName(actName);
		path.setActType(eventImpl.getActivityType());
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
		
		//如果是多实例任务，则在创建活动时生成任务执行人列表

		if(nodeParam.isMulti()==true)
		{
			WorkflowNodeAssignee nodeAssigneeParam = configWorkflowService.getNodeAssignee(workflowVersionId, eventImpl.getActivityId());
			List<SysAccount> list = workgroupService.getWorkgroupSysAccounts(nodeAssigneeParam.getAssigneeParam());
			List<String> assigneeList = new ArrayList<String>();
			for(SysAccount item : list)
			{
				assigneeList.add(item.getAccountId());
			}
			runtimeService.setVariable(executionEntity.getId(), "assigneeList", assigneeList);
		}
	}

}
