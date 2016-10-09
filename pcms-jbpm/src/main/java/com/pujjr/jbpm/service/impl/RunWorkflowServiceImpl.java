package com.pujjr.jbpm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.core.command.ProcessNextCommand;
import com.pujjr.jbpm.core.command.StartProcessCommand;
import com.pujjr.jbpm.core.ProcessHandleHelper;
import com.pujjr.jbpm.dao.WorkflowDefineMapper;
import com.pujjr.jbpm.dao.WorkflowGlobalParamMapper;
import com.pujjr.jbpm.domain.WorkflowDefine;
import com.pujjr.jbpm.domain.WorkflowGlobalParam;
import com.pujjr.jbpm.domain.WorkflowNodeParam;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.domain.WorkflowVersion;
import com.pujjr.jbpm.core.handle.IProcessCreateAfterHandle;
import com.pujjr.jbpm.core.handle.IProcessCreatePreHandle;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.jbpm.vo.ReCommitMode;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;
import com.pujjr.utils.SpringBeanUtils;

@Service
public class RunWorkflowServiceImpl implements IRunWorkflowService
{
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private IConfigWorkflowService  configWorkflowService;
	
	
	public ProcessInstance startProcess(String workflowKey,String businessKey,Map<String,Object> vars)
	{
		if(vars == null)
		{
			vars = new HashMap<String,Object>();
		}
		WorkflowDefine workflowDefine = configWorkflowService.getWorkflowDefineByDefineKey(workflowKey);
		
		StartProcessCommand startProcessCommand = new StartProcessCommand();
		startProcessCommand.setProcessDefinitionKey(workflowKey);
		startProcessCommand.setBusinessKey(businessKey);
		//获取版本激活版本信息
		WorkflowVersion version = configWorkflowService.getActivateVersionByDefineId(workflowDefine.getId());
		vars.put(ProcessGlobalVariable.WORKFLOW_VERSION_ID, version.getId());
		//获取流程全局参数设置
		WorkflowGlobalParam globalParam =configWorkflowService.getGlobalParamByVersionId(version.getId());
		//执行流程创建前处理
		if(globalParam!=null && globalParam.getWorkflowCreatePrehandle() !=null && globalParam.getWorkflowCreatePrehandle() !="")
		{
			IProcessCreatePreHandle preHandle = (IProcessCreatePreHandle)SpringBeanUtils.getBean(globalParam.getWorkflowCreatePrehandle());
			preHandle.handle(startProcessCommand);
		}
		//启动流程
		startProcessCommand.setCommandType(CommandType.START);
		ProcessHandleHelper.setProcessCommand(startProcessCommand);
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(version.getActivitiProcdefId(),businessKey,vars);
		
		//如设置了跳过第一个节点，判断此节点是否第一个人工任务节点，如是则自动完成此任务
		if(globalParam !=null &&(globalParam.getIsJumpFirstTask()!=null && globalParam.getIsJumpFirstTask()!=false))
		{
			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			ProcessNextCommand processNextCommand = new ProcessNextCommand();
			processNextCommand.setCommandType(CommandType.COMMIT);
			processNextCommand.setTaskId(task.getId());
			processNextCommand.setAssignee(task.getAssignee());
			completeTask(processNextCommand);
		}		
		//执行流程创建后处理
		if(globalParam!=null && globalParam.getWorkflowCreateAfterhandle() !=null && globalParam.getWorkflowCreateAfterhandle() !="")
		{
			IProcessCreateAfterHandle afterHandle = (IProcessCreateAfterHandle)SpringBeanUtils.getBean(globalParam.getWorkflowCreateAfterhandle());
			afterHandle.handle(startProcessCommand,processInstance);
		}
		return processInstance;
	}
	
	public void completeTask(ProcessNextCommand processNextCommand)
	{
		Task task = taskService.createTaskQuery().taskId(processNextCommand.getTaskId()).singleResult();	
		processNextCommand.setActId(task.getTaskDefinitionKey());
		//获取流程版本信息
		String workflowVersionId = runtimeService.getVariable(task.getProcessInstanceId(),ProcessGlobalVariable.WORKFLOW_VERSION_ID).toString();
		//获取节点参数配置
		WorkflowNodeParamVo nodeParam = configWorkflowService.getWorkflowNodeParam(workflowVersionId, task.getTaskDefinitionKey());
		
		//获取当前任务节点执行路径信息
		WorkflowRunPath runPath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(), task.getTaskDefinitionKey());
		
		//如果是回退操作，先获取可回退节点的回退路径
		if(processNextCommand.getCommandType().equals(CommandType.BACK))
		{
			//如果配置了返回节点则直接跳转对应节点,设置回退节点至线程变量中去
			if(nodeParam.getBackNodeId()!=null && nodeParam.getBackNodeId()!="")
			{
				WorkflowRunPath backNodeRunPath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(), nodeParam.getBackNodeId());
				ProcessHandleHelper.setBackNode(backNodeRunPath);
				processNextCommand.setDestNodeId(nodeParam.getBackNodeId());
			}
			else
			{
				WorkflowRunPath backNodeRunPath = getBackNodeRunPath(task.getProcessDefinitionId(),task.getProcessInstanceId(),task.getTaskDefinitionKey());
				ProcessHandleHelper.setBackNode(backNodeRunPath);
				processNextCommand.setDestNodeId(backNodeRunPath.getActId());
			}
			
		}
		//如果是正常提交，则先判断此节点是否由回退产生，是则需要判断提交方式，1、按原路径走，2、按流程路径走
		else
		{
			
			if((runPath != null) &&(runPath.getInJumpType().equals(CommandType.BACK) || runPath.getInJumpType().equals(CommandType.BACT_TO_STARTER)))
			{
				//如果设置为原路径返回则原路径，如果没设置则默认原路径返回
				if(nodeParam.getRecommitMode().equals(ReCommitMode.BACK_PATH)||nodeParam.getRecommitMode()==null)
				{
					processNextCommand.setDestNodeId(runPath.getParentPathId());
				}
			}
			
		}
		//帮当前路径ID加入线程变量让后续的任务能识别出是哪个人工任务提交的
		processNextCommand.setCurRunPathId(runPath.getId());
		
		//保存线程信息
		ProcessHandleHelper.setProcessCommand(processNextCommand);
		
		if(processNextCommand.getDestNodeId()!=null)
		{
			this.jumpToNodes(task.getId(), new String[] { processNextCommand.getDestNodeId() }, processNextCommand.getVars());
		}
		else
		{
			taskService.complete(task.getId(),processNextCommand.getVars());
		}
		
		ProcessHandleHelper.clearProcessCommand();
		ProcessHandleHelper.clearBackNode();
		
	}
	/**
	 * 获取当前节点退回的节点信息
	 * @param processDefinitionId 流程定义ID
	 * @param processInstanceId 流程实例ID
	 * @param actId 节点ID
	 * @return workflowRunPath 回退节点上一次运行路径信息
	 * **/
	private WorkflowRunPath getBackNodeRunPath(String processDefinitionId,String processInstanceId,String actId)
	{
		//获取流程定义
		ProcessDefinitionEntity def =  (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		//找到当前任务对应的活动定义信息
		ActivityImpl curAct = def.findActivity(actId);
		//获取入口信息
		List<PvmTransition> incomingTransitions = curAct.getIncomingTransitions();
		
		PvmActivity backAct =  incomingTransitions.get(0).getSource();
		
		return runPathService.getFarestRunPathByActId(processInstanceId, backAct.getId());
	}
	
	/**
	 * 完成流程跳转节点的转向路径操作
	 * @param processDefinitionId  流程定义ID
	 * @param taskDefinitionKey 任务定义Key
	 * @param destActivityIds 目的活动定义ID列表
	 * **/
	@SuppressWarnings("unchecked")
	private Map<String,Object> prepareJump(String processDefinitionId, String taskDefinitionKey, String[] destActivityIds )
	{
		Map<String,Object>  map = new HashMap<String,Object>();
		//获取流程定义
		ProcessDefinitionEntity def =  (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		//找到当前任务对应的活动定义信息
		ActivityImpl curAct = def.findActivity(taskDefinitionKey);
		//获取出口信息
		List<PvmTransition> outTrans= curAct.getOutgoingTransitions();  
		List<PvmTransition> cloneOutTrans = new ArrayList<PvmTransition>(Arrays.asList(new PvmTransition[outTrans.size()])); ;
		Collections.copy(cloneOutTrans, outTrans);
		map.put("cloneOutTrans", cloneOutTrans);
		
		//清除出口信息
		curAct.getOutgoingTransitions().clear();
		//创建到目的节点的路径
		for(String destId:destActivityIds)
		{
			ActivityImpl destAct = def.findActivity(destId);
			TransitionImpl transitionImpl = curAct.createOutgoingTransition();
			transitionImpl.setDestination(destAct);
		}
		map.put("curAct", curAct);
		return map;
	}
	/**
	 * 恢复跳转后的原始Activity路径
	 * @param map 保存原始Activity信息
	 * **/
	@SuppressWarnings("unchecked")
	private void restoreJump(Map<String,Object> map)
	{
		ActivityImpl curAct = (ActivityImpl)map.get("curAct");
		List<PvmTransition> cloneOutTrans  = (List<PvmTransition>)map.get("cloneOutTrans");
		curAct.getOutgoingTransitions().clear();
		curAct.getOutgoingTransitions().addAll(cloneOutTrans);
	}
	/**
	 * 通过指定目标节点实现流程任务条状
	 * @param taskId 任务ID
	 * @param destNodeIds 目标节点ID
	 * **/
	private void jumpToNodes(String taskId,String[] destActivityIds,Map<String,Object> vars)
	{
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String processDefinitionId = task.getProcessDefinitionId();
		String taskDefinitionKey = task.getTaskDefinitionKey();			
		Map<String,Object> map = prepareJump(processDefinitionId,taskDefinitionKey,destActivityIds);
		taskService.complete(taskId,vars); 
		restoreJump(map);
	}

	@Override
	public void completeTask(String taskId, String message,HashMap<String,Object> vars, CommandType commandType) {
		// TODO Auto-generated method stub
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessNextCommand processNextCommand = new ProcessNextCommand();
		processNextCommand.setCommandType(commandType);
		processNextCommand.setTaskId(taskId);
		processNextCommand.setAssignee(task.getAssignee());
		processNextCommand.setVars(vars);
		processNextCommand.setMessage(message);
		completeTask(processNextCommand);
	}
}
