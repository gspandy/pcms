package com.pujjr.carcredit.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.codehaus.groovy.runtime.dgmimpl.arrays.ArrayGetAtMetaMethod;
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
import com.mysql.fabric.xmlrpc.base.Array;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.ContractInfo;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.IBankService;
import com.pujjr.base.service.IContractService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.constant.ApplyStatus;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.domain.ApplyFinance;
import com.pujjr.carcredit.domain.AutoAssigneeConfig;
import com.pujjr.carcredit.domain.CallBackResult;
import com.pujjr.carcredit.domain.CancelApplyInfo;
import com.pujjr.carcredit.domain.ChangeApplyInfo;
import com.pujjr.carcredit.domain.LoanCheck;
import com.pujjr.carcredit.domain.Reconsider;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.carcredit.po.QueryParamToDoTaskPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.po.WorkflowProcessResultPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyApproveVo;
import com.pujjr.carcredit.vo.ApplyCheckVo;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.AutoAssigneeConfigVo;
import com.pujjr.carcredit.vo.CancelApplyInfoVo;
import com.pujjr.carcredit.vo.ChangeApplyInfoVo;
import com.pujjr.carcredit.vo.OnlineAcctVo;
import com.pujjr.carcredit.vo.QueryParamToDoTaskVo;
import com.pujjr.carcredit.vo.ReconsiderApplyVo;
import com.pujjr.carcredit.vo.ReconsiderApproveVo;
import com.pujjr.carcredit.vo.SignContractVo;
import com.pujjr.carcredit.vo.SignFinanceDetailVo;
import com.pujjr.carcredit.vo.TaskApproveCommitVo;
import com.pujjr.carcredit.vo.TaskAssigneeVo;
import com.pujjr.carcredit.vo.TaskCheckCommitVo;
import com.pujjr.carcredit.vo.TaskLoanApproveVo;
import com.pujjr.carcredit.vo.TaskVo;
import com.pujjr.carcredit.vo.ToDoTaskVo;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.utils.Utils;

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
	@Autowired
	private ISignContractService signContractService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private IBankService bankService;
	@Autowired
	private IContractService contractService;
	@Autowired
	private RuntimeService runtimeService;
	
	@RequestMapping(value="/todolist",method=RequestMethod.GET)
	public PageVo getToDoTaskList(QueryParamToDoTaskVo param,HttpServletRequest request)
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		QueryParamToDoTaskPo queryParam = new QueryParamToDoTaskPo();
		BeanUtils.copyProperties(param, queryParam);
		queryParam.setAssignee(sysAccount.getAccountId());
		
		if(param.getInTaskDefKeys()!=null&& param.getInTaskDefKeys()!="")
		{
			String[] arrInTaskDefKey = param.getInTaskDefKeys().split(",");
			queryParam.setInTaskDefKeyList(Arrays.asList(arrInTaskDefKey));
		}
		if(param.getOutTaskDefKeys()!=null && param.getOutTaskDefKeys() != "")
		{
			String[] arrOutTaskDefKey = param.getOutTaskDefKeys().split(",");
			queryParam.setOutTaskDefKeyList(Arrays.asList(arrOutTaskDefKey));
		}
		
		List<ToDoTaskPo> poList = taskService.getToDoTaskList(queryParam);
		
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
	
	@ResponseBody
	@RequestMapping(value="/commitReApplyTask/{taskId}",method=RequestMethod.POST)
	public void commitReApplyTask(@PathVariable String taskId,@RequestBody ApplyVo applyVo,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitReApplyTask(applyVo, taskId, sysAccount.getAccountId());
	}
	
	@ResponseBody
	@RequestMapping(value="/commitSupplyCheckTask/{taskId}",method=RequestMethod.POST)
	public void commitSupplyCheckTask(@PathVariable String taskId,@RequestBody ApplyVo applyVo,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitSupplyCheckTask(applyVo, taskId, sysAccount.getAccountId());
	}
	
	@RequestMapping(value="/{taskId}",method=RequestMethod.GET)
	public TaskVo getTaskByTaskId(@PathVariable String taskId) throws Exception
	{
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null)
		{
			throw new Exception("查询任务失败,任务ID"+taskId+"对应任务不存在 ");
		}
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		//变更申请单任务状态为处理中
		Apply apply = applyService.getApply(businessKey);
		switch(apply.getStatus())
		{
			case ApplyStatus.WAIT_CHECK:
				apply.setStatus(ApplyStatus.CHECKING);
				break;
			case ApplyStatus.WAIT_APPROVE:
				apply.setStatus(ApplyStatus.APPROVING);
				break;
			case ApplyStatus.WAIT_LOAN_CHECK:
				apply.setStatus(ApplyStatus.LOAN_CHECKING);
				break;
			case ApplyStatus.WAIT_RECONSIDER:
				apply.setStatus(ApplyStatus.RECONSIDERING);
				break;
			case ApplyStatus.WAIT_CHANGE_APPROVE:
				apply.setStatus(ApplyStatus.CHANGE_APPROVING);
				break;
			case ApplyStatus.WAIT_CANCEL_APPRVOE:
				apply.setStatus(ApplyStatus.CANCEL_APPROVING);
				break;
		}
		applyService.updateApply(apply);
		
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
	@RequestMapping(value="/commitPreCheckTask/{taskId}",method=RequestMethod.POST)
	public void commitPreCheckTask(@PathVariable String taskId,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitPreCheckTask(taskId, sysAccount.getAccountId());
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
		List<OnlineAcctPo> poList = taskService.getOnlineAcctInfo(group.getId(),false);
		if(poList == null)
		{
			return null;
		}
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
			result.put("taskName", task.getTaskName());
			result.put("productName", task.getProductName());
			result.put("tenantName", task.getTenantName());
			String businessKey = task.getBusinessKey();
			ApplyVo apply = applyService.getApplyDetail(businessKey);
			SysBranch sysBranch = sysBranchService.getSysBranch(null, apply.getCreateBranchCode());
			SysWorkgroup group = workgroupService.getWorkgroupByName(sysParam.getParamValue());
			ProcessTaskUserBo assignee = taskService.getProcessTaskAccount(apply.getProductCode(), apply.getTotalFinanceAmt(), sysBranch.getId(), group.getId(), candidateAccounts);
			if(assignee != null)
			{
				HashMap<String,Object> vars = new HashMap<String,Object>();
				vars.put("checkAssignee", assignee.getAccountId());
				runWorkflowService.completeTask(task.getTaskId(), "", vars, CommandType.COMMIT);
				result.put("procResult", "分配成功，任务执行人"+assignee.getAccountId());
				result.put("procStatus", true);
			}
			else
			{
				result.put("procResult", "未找到满足条件的任务执行者");
				result.put("procStatus", false);
			}
			procResultList.add(result);
			
		}
		return procResultList;
	}
	@RequestMapping(value="/querySignInfo/{appId}",method=RequestMethod.GET)
	public SignContractVo getSignInfo(@PathVariable String appId)
	{
		SignContractVo signVo = new SignContractVo();
		List<SignFinanceDetailVo>  dtlListVo = new ArrayList<SignFinanceDetailVo>();
		SignContract signPo = signContractService.getSignContractByAppId(appId);
		if(signPo==null)
		{
			signPo = new SignContract();
			signPo.setSignStep(1);  
			signPo.setAppId(appId);
		}
		BeanUtils.copyProperties(signPo, signVo);  
		
		ApplyVo applyVo = applyService.getApplyDetail(appId);
		signVo.setRepayAcctName(applyVo.getTenant().getName());
		SysBranch sysBranch = sysBranchService.getSysBranch(null, applyVo.getCreateBranchCode());
		SysBranchDealer dealer = sysBranchService.getDealerByBranchId(sysBranch.getId());
		String loanBankName = bankService.getBankInfoById(dealer.getBankId()).getBankName();
		signVo.setLoanBankName(loanBankName);
		signVo.setLoanSubBranch(dealer.getLoanSubbranch());
		signVo.setLoanAcctNo(dealer.getLoanAcctNo());
		signVo.setLoanAcctName(dealer.getLoanAcctName());
		List<ApplyFinanceVo> applyFinanceListVo = applyVo.getFinances();
		for(ApplyFinanceVo l:applyFinanceListVo)
		{
			//查询申请融资信息签约信息
			SignFinanceDetail dtl = signContractService.getSignFinanceDetailByApplyFinanceId(l.getId());
			//如果没有签约融资信息则车架号、发动机号、车辆颜色以申请单为准
			if(dtl==null)
			{
				dtl = new SignFinanceDetail();
				dtl.setCarColor(l.getCarColor());
				dtl.setCarVin(l.getCarVin());
				dtl.setCarEngineNo(l.getCarEngineNo());
				
			}
		
			SignFinanceDetailVo dtlVo = new SignFinanceDetailVo();
			dtlVo.setApplyFinance(l);
			dtlVo.setSignFinanceDetail(dtl);
			dtlListVo.add(dtlVo);
		}
		signVo.setSignFinanceList(dtlListVo);
		//查询放款复核信息
		LoanCheck loanCheck = taskService.getLoanCheckInfoByAppId(appId);
		signVo.setLoanCheck(loanCheck);
		return signVo;
	}
	@RequestMapping(value="/saveSignContractInfo",method=RequestMethod.POST)
	public void saveSignContractInfo(@RequestBody SignContractVo params,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.saveSignContractInfo(params, sysAccount.getAccountId());
	}
	
	@RequestMapping(value="/commitSignContractTask/{appId}/{taskId}",method=RequestMethod.POST)
	public void commitSignContractTask(@PathVariable String taskId,@PathVariable String appId,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitSignContract(appId, taskId, sysAccount.getAccountId());
	}
	
	@RequestMapping(value="/saveLoanCheckInfo",method=RequestMethod.POST)
	public void saveLoanCheckInfo(@RequestBody SignContractVo params,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.saveLoanCheckInfo(params,sysAccount.getAccountId());
	}
	
	@RequestMapping(value="/commitSupplyLoanCheckTask/{taskId}",method=RequestMethod.POST)
	public void commitSupplyLoanCheckTask(@PathVariable String taskId,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitSupplyLoanCheckTask(taskId, sysAccount.getAccountId());
	}
	
	@RequestMapping(value="/commitLoanCheckTask/{taskId}/{commitType}",method=RequestMethod.POST)
	public void commitLoanCheckTask(@PathVariable String taskId,@PathVariable String commitType,@RequestBody SignContractVo params,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitLoanCheck(params,commitType, taskId, sysAccount.getAccountId());
	}
	@RequestMapping(value="/commitPrevLoanApproveTask/{taskId}",method=RequestMethod.POST)
	public void commitPrevLoanApproveTask(@PathVariable String taskId,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitPrevLoanApprove(taskId, sysAccount.getAccountId());
	}
	@RequestMapping(value="/commitLoanApproveTask/{taskId}",method=RequestMethod.POST)
	public void commitLoanApproveTask(@PathVariable String taskId,@RequestBody TaskLoanApproveVo params,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitLoanApprove(params,taskId, sysAccount.getAccountId());
	}
	@RequestMapping(value="/getReconsiderInfo/{taskId}",method=RequestMethod.GET)
	public ReconsiderApplyVo getReconsiderInfo(@PathVariable String taskId) throws Exception
	{
		//获取拒绝复议上级任务路径
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null)
		{
			throw new Exception("提交任务失败,任务ID"+taskId+"对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(), task.getTaskDefinitionKey());
		if(runpath == null)
		{
			throw new Exception("提交任务失败,任务ID"+taskId+"对应路径不存在 ");
		} 
		String parentUserTaskPathId = runpath.getParentUsertaskPathId();
		//获取上级任务处理结果
		TaskProcessResult parentUserTaskProcessResult = taskService.getTaskProcessResultByPathId(parentUserTaskPathId);
		
		ReconsiderApplyVo vo = new ReconsiderApplyVo();
		vo.setRejectReason(parentUserTaskProcessResult.getProcessResultDesc());
		return vo;
	}
	@RequestMapping(value="/commitReconsiderApplyTask/{appId}/{taskId}",method=RequestMethod.POST)
	public void commitReconsiderApplyTask(@RequestBody ReconsiderApplyVo params,@PathVariable String taskId,@PathVariable String appId,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitReconsiderApply(params, appId, taskId, sysAccount.getAccountId());
	}
	@RequestMapping(value="/getReconsiderApproveInfo/{appId}",method=RequestMethod.GET)
	public ReconsiderApproveVo getReconsiderApproveInfo(@PathVariable String appId)
	{
		Reconsider po = taskService.getEnabledReconsiderByAppId(appId);
		ReconsiderApproveVo vo = new ReconsiderApproveVo();
		BeanUtils.copyProperties(po, vo);
		return vo;
	}
	
	@RequestMapping(value="/commitReconsiderApproveTask/{taskId}",method=RequestMethod.POST)
	public void commitReconsiderApproveTask(@RequestBody ReconsiderApproveVo params,@PathVariable String taskId,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitReconsiderApprove(params, taskId, sysAccount.getAccountId());
	}
	@RequestMapping(value="/backTask/{taskId}",method=RequestMethod.POST)
	public void backTask(@PathVariable String taskId,@RequestBody String message)
	{
		taskService.backTask(taskId, message);
	}
	@RequestMapping(value="/getWorkflowProcessResult/{taskId}",method=RequestMethod.GET)
	public List<WorkflowProcessResultPo> getWorkflowProcessResult(@PathVariable String taskId) throws Exception
	{
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null)
		{
			throw new Exception("提交任务失败,任务ID"+taskId+"对应任务不存在 ");
		}
		return taskService.getWorkflowProcessResult(task.getProcessInstanceId());
	}
	@RequestMapping(value="/commitCallBackTask/{appId}/{taskId}",method=RequestMethod.POST)
	public void commitCallBackTask(@RequestBody CallBackResult result,@PathVariable String appId,@PathVariable String taskId,HttpServletRequest request) throws Exception
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitCallBackTask(result, appId, taskId, sysAccount.getAccountId());
	}
	@RequestMapping(value="/commitChangeApplyInfoTask/{appId}/{taskId}",method=RequestMethod.POST)
	public void commitChangeApplyInfoTask(@RequestBody ChangeApplyInfo info,@PathVariable String appId,@PathVariable String taskId) throws Exception
	{
		taskService.commitChangeApplyInfoTask(info, appId, taskId);
	}
	
	@RequestMapping(value="/getLatestChangeApplyInfo/{taskId}",method=RequestMethod.GET)
	public ChangeApplyInfoVo getLatestChangeApplyInfo(@PathVariable String taskId) throws Exception
	{
		ChangeApplyInfoVo vo = new ChangeApplyInfoVo();
		ChangeApplyInfo info = taskService.getLatestChangeApplyInfo(taskId);
		BeanUtils.copyProperties(info, vo);
		return vo;
	}
	@RequestMapping(value="/commitApproveChangeApplyInfoTask/{appId}/{taskId}",method=RequestMethod.POST)
	public void  commitApproveChangeApplyInfoTask(@RequestBody ChangeApplyInfoVo vo ,@PathVariable String appId,@PathVariable String taskId) throws Exception
	{
		taskService.commitApproveChangeApplyInfoTask(vo, appId, taskId);
	}
	
	@RequestMapping(value="/commitCancelApplyInfoTask/{appId}/{taskId}",method=RequestMethod.POST)
	public void commitCancelApplyInfoTask(@RequestBody CancelApplyInfo info,@PathVariable String appId,@PathVariable String taskId) throws Exception
	{
		taskService.commitCancelApplyTask(info, appId, taskId);
	}
	
	@RequestMapping(value="/getLatestCancelApplyInfo/{taskId}",method=RequestMethod.GET)
	public CancelApplyInfoVo getLatestCancelApplyInfo(@PathVariable String taskId) throws Exception
	{
		CancelApplyInfoVo vo = new CancelApplyInfoVo();
		CancelApplyInfo info = taskService.getLatestCancelApplyInfo(taskId);
		BeanUtils.copyProperties(info, vo);
		return vo;
	}
	@RequestMapping(value="/commitApproveCancelApplyInfoTask/{appId}/{taskId}",method=RequestMethod.POST)
	public void  commitApproveCancelApplyInfoTask(@RequestBody CancelApplyInfoVo vo ,@PathVariable String appId,@PathVariable String taskId) throws Exception
	{
		taskService.commitApprvoeCancelApply(vo, appId, taskId);
	}
	@RequestMapping(value="/getAutoAssigneeConfigInfo",method=RequestMethod.GET)
	public AutoAssigneeConfigVo getAutoAssigneeConfigInfo() throws ParseException
	{
		AutoAssigneeConfig config =  taskService.getAutoAssigneeConfigInfo();
		AutoAssigneeConfigVo vo = new AutoAssigneeConfigVo();
		vo.setId(config.getId());
		vo.setEnabled(config.getEnabled());
		String startDate = Utils.getFormatDate(config.getStartTime(), "yyyyMMdd");
		String startHour = Utils.getFormatDate(config.getStartTime(), "HH");
		String startMinute = Utils.getFormatDate(config.getStartTime(), "mm");
		String endDate = Utils.getFormatDate(config.getEndTime(), "yyyyMMdd");
		String endHour = Utils.getFormatDate(config.getEndTime(), "HH");
		String endMinute = Utils.getFormatDate(config.getEndTime(), "mm");
		vo.setStartDate(Utils.str82date(startDate));
		vo.setStartHour(startHour);
		vo.setStartMinute(startMinute);
		vo.setEndDate(Utils.str82date(endDate));
		vo.setEndHour(endHour);
		vo.setEndMinute(endMinute);
		
		return vo;
	}
	@RequestMapping(value="/setAutoAssigneeConfigInfo",method=RequestMethod.POST)
	public void setAutoAssigneeConfigInfo(@RequestBody AutoAssigneeConfigVo vo) throws Exception
	{
		AutoAssigneeConfig po = new AutoAssigneeConfig();
		po.setId(vo.getId());
		po.setEnabled(vo.isEnabled());
		//如果是开启自动分单，需进行参数判断
		if(vo.isEnabled())
		{
			if(vo.getStartDate()==null||vo.getStartHour()==null||vo.getStartMinute()==null||vo.getStartHour()==""||vo.getStartMinute()=="")
			{
				throw new Exception("开始时间不能为空");
			}
			if(vo.getEndDate()==null || vo.getEndHour()==null || vo.getEndMinute()==null || vo.getEndHour()==""||vo.getEndMinute()=="")
			{
				throw new Exception("结束时间不能为空 ");
			}
			
			String startTime = Utils.getFormatDate(vo.getStartDate(), "yyyy-MM-dd")+" "+vo.getStartHour()+":"+vo.getStartMinute();
			Date start = Utils.str2time(startTime);
			po.setStartTime(start);
			String endTime = Utils.getFormatDate(vo.getEndDate(), "yyyy-MM-dd")+" "+vo.getEndHour()+":"+vo.getEndMinute();
			Date end = Utils.str2time(endTime);
			if(Utils.compareDateTime(start, end)<0)
			{
				throw new Exception("开始时间不能大于结束时间 ");
			}
			po.setEndTime(Utils.str2time(endTime));
		}
		
		taskService.setAutoAssigneeConfigInfo(po);
	}
	@RequestMapping(value="/getContractInfoListByAppId/{appId}",method=RequestMethod.GET)
	public List<ContractInfo>  getContractInfoListByAppId(@PathVariable String appId)
	{
		Apply apply = applyService.getApply(appId);
		SysBranch sysBranch = sysBranchService.getSysBranch(null, apply.getCreateBranchCode());
		SysBranchDealer dealer = sysBranchService.getDealerByBranchId(sysBranch.getId());
		return contractService.getContractInfoListByContractTemplateId(dealer.getReserver1(), true);
	}
	
	@RequestMapping(value="/getUserTaskDefineGroupInfo",method=RequestMethod.GET)
	public List<HashMap<String,Object>> getUserTaskDefineGroupInfo(QueryParamToDoTaskVo param,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		QueryParamToDoTaskPo queryParam = new QueryParamToDoTaskPo();
		BeanUtils.copyProperties(param, queryParam);
		queryParam.setAssignee(sysAccount.getAccountId());
		
		if(param.getInTaskDefKeys()!=null&& param.getInTaskDefKeys()!="")
		{
			String[] arrInTaskDefKey = param.getInTaskDefKeys().split(",");
			queryParam.setInTaskDefKeyList(Arrays.asList(arrInTaskDefKey));
		}
		if(param.getOutTaskDefKeys()!=null && param.getOutTaskDefKeys() != "")
		{
			String[] arrOutTaskDefKey = param.getOutTaskDefKeys().split(",");
			queryParam.setOutTaskDefKeyList(Arrays.asList(arrOutTaskDefKey));
		}
		return taskService.getUserTaskDefineGroupInfo(queryParam);
	}
	@RequestMapping(value="/batchDoLoanTask",method=RequestMethod.POST)
	public void batchDoLoanTask(@RequestBody List<ToDoTaskVo> list)
	{
		taskService.batchDoLoanTask(list);
	}

}
