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
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyCheckVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.TaskCheckCommitVo;
import com.pujjr.carcredit.vo.TaskVo;
import com.pujjr.carcredit.vo.ToDoTaskVo;

@RestController
@RequestMapping("/task")
public class TaskController 
{
	@Autowired
	private ITaskService taskService;
	@Autowired
	private TaskService actTaskService;
	
	@RequestMapping(value="/todolist",method=RequestMethod.GET)
	public PageVo getToDoTaskList(String curPage,String pageSize,HttpServletRequest request)
	{
		PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize),true);
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		List<ToDoTaskPo> poList = taskService.getToDoTaskListByAccountId(sysAccount.getAccountId());
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
}
