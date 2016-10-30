package com.pujjr.jbpm.core.listeners;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
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

public class ActivitiCompletedListener implements EventHandler 
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
		ActivitiActivityEventImpl  eventImpl = ( ActivitiActivityEventImpl)event;
		
		//执行活动结束脚本
		String workflowVersionId = runtimeService.getVariable(eventImpl.getExecutionId(), "workflowVersionId").toString();
		WorkflowNodeParamVo nodeParam = configWorkflowService.getWorkflowNodeParam(workflowVersionId,eventImpl.getActivityId());
		if (nodeParam!= null && nodeParam.getActEndScript() != null && !nodeParam.getActEndScript().equals(""))
		{
			Map<String, Object> vars = runtimeService.getVariables(eventImpl.getExecutionId());
			GroovyEngine.execScript(nodeParam.getActEndScript(),eventImpl.getExecutionId(), vars);
		}
		
		//更新历史路径信息	
		WorkflowRunPath path = runPathService.getFarestRunPathByActId(eventImpl.getProcessInstanceId(), eventImpl.getActivityId());
		path.setEndTime(new Date());
		path.setOutJumpType(cmd.getCommandType().toString());
		if(cmd instanceof ProcessNextCommand)
		{
			ProcessNextCommand nextCmd = (ProcessNextCommand)cmd;
			if(nextCmd.getMessage() == null&&nextCmd.getCommandType().equals(CommandType.COMMIT))
			{
				nextCmd.setMessage("同意");
			}
			else
			{
				path.setMessage(cmd.getMessage());
			}
		}
		runPathService.updateWorkflowRunPath(path);
		
		cmd.setActId(eventImpl.getActivityId());
		cmd.setMessage(null);
		ProcessHandleHelper.setProcessCommand(cmd);
	}

}
