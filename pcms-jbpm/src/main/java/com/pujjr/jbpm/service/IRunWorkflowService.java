package com.pujjr.jbpm.service;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.core.command.ProcessNextCommand;
import com.pujjr.jbpm.core.command.StartProcessCommand;

@Service
@Transactional(rollbackFor=Exception.class)
public interface IRunWorkflowService 
{
	public ProcessInstance startProcess(String workflowKey,String businessKey,Map<String,Object> vars);
	
	public void completeTask(ProcessNextCommand processNextCommand);
	
	public void completeTask(String taskId,String message,HashMap<String,Object> vars,CommandType commandType);
	
}
