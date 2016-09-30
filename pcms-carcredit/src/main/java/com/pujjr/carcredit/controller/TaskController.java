package com.pujjr.carcredit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyApproveVo;
import com.pujjr.carcredit.vo.ApplyCheckVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.OnlineAcctVo;
import com.pujjr.carcredit.vo.TaskApproveCommitVo;
import com.pujjr.carcredit.vo.TaskAssigneeVo;
import com.pujjr.carcredit.vo.TaskCheckCommitVo;
import com.pujjr.carcredit.vo.TaskVo;
import com.pujjr.carcredit.vo.ToDoTaskVo;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.service.IRunWorkflowService;

@RestController
@RequestMapping("/task")
public class TaskController extends BaseController
{
	@Autowired
	private ITaskService taskService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private ISysParamService sysParamService;
	@Autowired
	private ISysWorkgroupService workgroupService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private IRunWorkflowService  runWorkflowService;
	
	@RequestMapping(value="/todolist/{queryType}",method=RequestMethod.GET)
	public PageVo getToDoTaskList(String curPage,String pageSize,@PathVariable String queryType,HttpServletRequest request)
	{
		PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize),true);
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		List<ToDoTaskPo> poList = taskService.getToDoTaskListByAccountId(sysAccount.getAccountId(),queryType);
		List<ToDoTaskVo> voList = new ArrayList<ToDoTaskVo>();
		for(ToDoTaskPo po : poList)
		{
			ToDoTaskVo vo = new ToDoTaskVo();
			BeanUtils.copyProperties(po,vo);
			voList.add(vo);
		}
		PageVo page=new PageVo();
		page.setTotalItem(((Page)poList).getTotal());
		page.setData(voList);
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value="/commitApplyTask",method=RequestMethod.POST)
	public void commitApplyTask(@RequestBody ApplyVo applyVo,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitApplyTask(applyVo, sysAccount.getAccountId());
	}
	
	@RequestMapping(value="/{taskId}",method=RequestMethod.GET)
	public TaskVo getTaskByTaskId(@PathVariable String taskId)
	{
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		TaskVo vo = new TaskVo();
		vo.setId(task.getId());
		vo.setName(task.getName());
		return vo;
	}
	@RequestMapping(value="/commitCheckTask/{taskId}",method=RequestMethod.POST)
	public void commitCheckTask(@PathVariable String taskId,@RequestBody TaskCheckCommitVo params,HttpServletRequest request) throws Exception
	{
		ApplyVo applyVo =params.getApplyVo();
		ApplyCheckVo checkVo =params.getCheckVo();
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitCheckTask(applyVo,checkVo, taskId,sysAccount.getAccountId());
	}
	
	@RequestMapping(value="/commitApproveTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveTask(@PathVariable String taskId,@RequestBody TaskApproveCommitVo params,HttpServletRequest request) throws Exception
	{
		ApplyVo applyVo =params.getApplyVo();
		ApplyApproveVo approveVo =params.getApproveVo();
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitApproveTask(applyVo,approveVo, taskId,sysAccount.getAccountId());
	}
	
	@RequestMapping(value="/getCheckWorkgroupOnlineAcct",method=RequestMethod.GET)
	public List<OnlineAcctVo> getCheckWorkgroupOnlineAcct() throws Exception
	{
		SysParam sysParam = sysParamService.getSysParamByParamName("check-group-name");
		if(sysParam ==null)
		{
			throw new Exception("未配置系统参数check-group-name");
		}
		SysWorkgroup group = workgroupService.getWorkgroupByName(sysParam.getParamValue());
		List<OnlineAcctPo> poList = taskService.getOnlineAcctInfo(group.getId());
		List<OnlineAcctVo> voList = new ArrayList<OnlineAcctVo>();
		for(OnlineAcctPo po : poList)
		{
			OnlineAcctVo vo = new OnlineAcctVo();
			BeanUtils.copyProperties(po, vo);
			voList.add(vo);
		}
		return voList;
	}
	@RequestMapping(value="/doCheckBatchAssigneeTask",method=RequestMethod.POST)
	public List<HashMap<String,Object>> doCheckBatchAssigneeTask(@RequestBody TaskAssigneeVo vo) throws Exception
	{
		List<ToDoTaskVo> toDoTaskList = vo.getToDoTaskList();
		List<String> candidateAccounts = vo.getAssingees();
		List<HashMap<String,Object>> procResultList = new ArrayList<HashMap<String,Object>>();
		SysParam sysParam = sysParamService.getSysParamByParamName("check-group-name");
		if(sysParam ==null)
		{
			throw new Exception("未配置系统参数check-group-name");
		}
		for(ToDoTaskVo task : toDoTaskList)
		{
			HashMap<String,Object> result = new HashMap<String,Object>();
			result.put("appId", task.getBusinessKey());
			String businessKey = task.getBusinessKey();
			ApplyVo apply = applyService.getUnCommitApplyDetail(businessKey);
			SysBranch sysBranch = sysBranchService.getSysBranch(null, apply.getCreateBranchCode());
			SysWorkgroup group = workgroupService.getWorkgroupByName(sysParam.getParamValue());
			String assignee = taskService.getProcessTaskAccount(apply.getProductCode(), 5000, sysBranch.getId(), group.getId(), candidateAccounts);
			if(assignee != null)
			{
				HashMap<String,Object> vars = new HashMap<String,Object>();
				vars.put("checkAssignee", assignee);
				runWorkflowService.completeTask(task.getTaskId(), "", vars, CommandType.COMMIT);
				result.put("procResult", "分配成功，任务执行人"+assignee);
			}
			else
			{
				result.put("procResult", "未找到满足条件的任务执行者");
			}
			procResultList.add(result);
			
		}
		return procResultList;
	}
}
