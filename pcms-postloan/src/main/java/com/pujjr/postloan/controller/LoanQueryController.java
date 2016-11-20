package com.pujjr.postloan.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamPageVo;
import com.pujjr.carcredit.constant.ApplyStatus;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.vo.TaskVo;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.postloan.domain.ApplyPublicRepay;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.ILoanQueryService;
import com.pujjr.postloan.service.IPublicRepayService;
import com.pujjr.postloan.vo.RepayFeeItemVo;

@RestController
@RequestMapping(value="/loanquery")
public class LoanQueryController 
{
	@Autowired
	private ILoanQueryService loanQueryService;
	@Autowired
	private IAccountingService accountingService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private IPublicRepayService publicRepayService;
	
	@RequestMapping(value="/getLoanCustList",method=RequestMethod.GET)
	public PageVo getLoanCustList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = loanQueryService.getLoanCustList();
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getLoanCustApplyInfo/{appId}",method=RequestMethod.GET)
	public HashMap<String,Object> getLoanCustApplyInfo(@PathVariable String appId)
	{
		return loanQueryService.getLoanCustApplyInfo(appId);
	}
	@RequestMapping(value="/getLoanCustNeedRepayInfo/{appId}",method=RequestMethod.GET)
	public RepayFeeItemVo getLoanCustNeedRepayInfo(@PathVariable String appId)
	{	
		RepayFeeItemVo feeItemVo = accountingService.getRepayingFeeItems(appId, false, null, false,true);
		return feeItemVo;
	}
	
	@RequestMapping(value="/getLoanCustRepayLog/{appId}",method=RequestMethod.GET)
	public PageVo getLoanCustRepayLog(@PathVariable String appId,QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = loanQueryService.getLoanCustRepayLog(appId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/getLoanCustChargeLog/{appId}",method=RequestMethod.GET)
	public PageVo getLoanCustChargeLog(@PathVariable String appId,QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = loanQueryService.getLoanCustChargeLog(appId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getLoanToDoTaskList",method=RequestMethod.GET)
	public PageVo getLoanToDoTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = loanQueryService.getLoanToDoTaskList(account.getAccountId());
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getTaskByTaskId/{taskId}/{workflowKey}",method=RequestMethod.GET)
	public TaskVo getTaskByTaskId(@PathVariable String taskId,@PathVariable String workflowKey) throws Exception
	{
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null)
		{
			throw new Exception("查询任务失败,任务ID"+taskId+"对应任务不存在 ");
		}
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		switch(workflowKey)
		{
			case  "DGHK":
				ApplyPublicRepay publicRepayPo = publicRepayService.getApplyPublicRepayTaskById(businessKey);
				if(publicRepayPo.getApplyStatus().equals(LoanApplyStatus.WaitingApprove.getName()))
				{
					publicRepayPo.setApplyStatus(LoanApplyStatus.Approving.getName());
					publicRepayService.modifyApplyPublicRepayInfo(publicRepayPo);
				}
				break;
		}
		
		TaskVo vo = new TaskVo();
		vo.setId(task.getId());
		vo.setName(task.getName());
		vo.setProcDefId(task.getProcessDefinitionId());
		vo.setProcInstId(task.getProcessInstanceId());
		WorkflowRunPath runPath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(), task.getTaskDefinitionKey());
		if(runPath.getProcessTime()==null)
		{
			runPathService.updateRunPathProcessTimeByTaskId(taskId);
		}
		//如果任务是被退回时，则在提示信息加入退回原因
		WorkflowRunPath parentRunPath = runPathService.getRunPathById(runPath.getParentUsertaskPathId());
		if(runPath.getInJumpType().equals(CommandType.BACK.name()))
		{
			vo.setTips("退回原因："+parentRunPath.getMessage());
		}else
		{
			vo.setTips(parentRunPath.getMessage());
		}
		return vo;
	}
	
	@RequestMapping(value="/getAfterCurrentPeriodRemainPeroidList/{appId}",method=RequestMethod.GET)
	List<Integer> getAfterCurrentPeriodRemainPeroidList(@PathVariable String appId)
	{
		return accountingService.getAfterCurrentPeriodRemainPeroidList(appId);
	}
}
