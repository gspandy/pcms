package com.pujjr.carcredit.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.core.handle.ITaskAssigneeHandle;
import com.pujjr.jbpm.domain.WorkflowNodeAssignee;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.AssigneeType;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.utils.SpringBeanUtils;

public class AutoAssigneeService 
{
	@Autowired
	private ITaskService taskService;
	@Autowired
	private ISysParamService sysParamService; 
	@Autowired 
	private ISysWorkgroupService workgroupService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private RuntimeService runtimeService;
	/**
	 * 功能：实现对已分配的审核任务进行自动分配
	 * **/
	public void autoAssigneeCheckTask() throws Exception
	{
		System.out.println("开始执行自动分配审核任务");
		//获取系统参数设置的自动分单任务执行账户信息
		SysParam sysParam = sysParamService.getSysParamByParamName("autoAssgineeCheckTaskAccountId");
		if(sysParam ==null)
		{
			throw new Exception("未配置系统参数autoAssgineeCheckTaskAccountId");
		}
		//获取此账户待执行任务列表
		List<ToDoTaskPo> todoTaskList = taskService.getToDoTaskListByAccountId(sysParam.getParamValue(), null);
		for(int i =0;i<todoTaskList.size();i++)
		{
			ToDoTaskPo task = todoTaskList.get(i);
			//获取当前在线审核组任务人员
			SysParam sysParamCheckGroupName = sysParamService.getSysParamByParamName("check-group-name");
			if(sysParamCheckGroupName ==null)
			{
				throw new Exception("未配置系统参数check-group-name");
			}
			SysWorkgroup group = workgroupService.getWorkgroupByName(sysParamCheckGroupName.getParamValue());
			List<OnlineAcctPo> poList = taskService.getOnlineAcctInfo(group.getId(),true);
			//如果没有则继续执行下一个任务
			if(poList == null)
			{
				continue;
			}
			List<String> candidateAccounts = new ArrayList<String>();
			for(OnlineAcctPo po : poList)
			{
				candidateAccounts.add(po.getAccountId());
			}
			//如果有则找出待选人员中符合执行条件的人员
			String businessKey = task.getBusinessKey();
			ApplyVo apply = applyService.getApplyDetail(businessKey);
			SysBranch sysBranch = sysBranchService.getSysBranch(null, apply.getCreateBranchCode());
			ProcessTaskUserBo assignee = taskService.getProcessTaskAccount(apply.getProductCode(), apply.getTotalFinanceAmt(), sysBranch.getId(), group.getId(), candidateAccounts);
			if(assignee != null)
			{
				HashMap<String,Object> vars = new HashMap<String,Object>();
				vars.put("checkAssignee", assignee.getAccountId());
				runWorkflowService.completeTask(task.getTaskId(), "", vars, CommandType.COMMIT);
			}
			else
			{
				continue;
			}
			
		}
	}
	/**
	 * 功能分配所有未找到任务执行者的任务
	 * @throws Exception 
	 * **/
	public void autoAssigneeNoOperTask() throws Exception
	{
		System.out.println("开始执行自动分配未找到任务执行者任务");
		//获取系统参数设置的自动分单任务执行账户信息
		SysParam sysParam = sysParamService.getSysParamByParamName("noAssigneeTaskProcessAccountId");
		if(sysParam ==null)
		{
			throw new Exception("未配置系统参数autoAssgineeCheckTaskAccountId");
		}
		//获取此账户待执行任务列表
		List<ToDoTaskPo> todoTaskList = taskService.getToDoTaskListByAccountId(sysParam.getParamValue(), null);
		for(int i =0;i<todoTaskList.size();i++)
		{
			ToDoTaskPo task = todoTaskList.get(i);
			//获取当前在线审核组任务人员
			SysParam sysParamCheckGroupName = sysParamService.getSysParamByParamName("check-group-name");
			if(sysParamCheckGroupName ==null)
			{
				throw new Exception("未配置系统参数check-group-name");
			}
			SysWorkgroup group = workgroupService.getWorkgroupByName(sysParamCheckGroupName.getParamValue());
			List<OnlineAcctPo> poList = taskService.getOnlineAcctInfo(group.getId(),true);
			//如果没有则继续执行下一个任务
			if(poList == null)
			{
				continue;
			}
			List<String> candidateAccounts = new ArrayList<String>();
			for(OnlineAcctPo po : poList)
			{
				candidateAccounts.add(po.getAccountId());
			}
			//如果有则找出待选人员中符合执行条件的人员
			String businessKey = task.getBusinessKey();
			ApplyVo apply = applyService.getApplyDetail(businessKey);
			SysBranch sysBranch = sysBranchService.getSysBranch(null, apply.getCreateBranchCode());
			ProcessTaskUserBo assignee = taskService.getProcessTaskAccount(apply.getProductCode(), apply.getTotalFinanceAmt(), sysBranch.getId(), group.getId(), candidateAccounts);
			if(assignee != null)
			{
				HashMap<String,Object> vars = new HashMap<String,Object>();
				vars.put("checkAssignee", assignee.getAccountId());
				runWorkflowService.completeTask(task.getTaskId(), "", vars, CommandType.COMMIT);
			}
			else
			{
				continue;
			}
			
		}		
	}
	
	private String getTaskAssignee(String taskId)
	{
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		String workflowVersionId = runtimeService.getVariable(task.getExecutionId(), ProcessGlobalVariable.WORKFLOW_VERSION_ID).toString();
		WorkflowNodeAssignee nodeAssigneeParam = configWorkflowService.getNodeAssignee(workflowVersionId, task.getTaskDefinitionKey());
		String assignee = null;
		String assigneeType = nodeAssigneeParam.getAssigneeType();
		if(assigneeType.equals(AssigneeType.FROM_VARIABLE))
		{
			assignee = runtimeService.getVariable(task.getExecutionId(),nodeAssigneeParam.getAssigneeParam()).toString();
		}
		else if(assigneeType.equals(AssigneeType.PROCESS_OWNER))
		{
			assignee = runtimeService.getVariable(task.getExecutionId(),ProcessGlobalVariable.WORKFLOW_OWNER).toString();
		}
		else if(assigneeType.equals(AssigneeType.ASSIGNEE_ACCOUNT))
		{
			assignee = nodeAssigneeParam.getAssigneeParam();
		}
		else if(assigneeType.equals(AssigneeType.ASSIGNEE_WORKGROUP))
		{
			ITaskAssigneeHandle handler = (ITaskAssigneeHandle)SpringBeanUtils.getBean(nodeAssigneeParam.getAssigneeHandle());
			//assignee = handler.handle(nodeAssigneeParam.getAssigneeParam(),taskEntity);
		}
		else
		{
			//assignee = taskEntity.getAssignee();
		}
		
		return assignee;
	}
}
