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
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.IBankService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.domain.ApplyFinance;
import com.pujjr.carcredit.domain.Reconsider;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.po.WorkflowProcessResultPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyApproveVo;
import com.pujjr.carcredit.vo.ApplyCheckVo;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.OnlineAcctVo;
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
	public TaskVo getTaskByTaskId(@PathVariable String taskId) throws Exception
	{
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null)
		{
			throw new Exception("提交任务失败,任务ID"+taskId+"对应任务不存在 ");
		}
		runPathService.updateRunPathProcessTimeByTaskId(taskId);
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
			}
			else
			{
				result.put("procResult", "未找到满足条件的任务执行者");
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
			SignFinanceDetailVo dtlVo = new SignFinanceDetailVo();
			dtlVo.setApplyFinance(l);
			dtlVo.setSignFinanceDetail(dtl);
			dtlListVo.add(dtlVo);
		}
		signVo.setSignFinanceList(dtlListVo);
		return signVo;
	}
	@RequestMapping(value="/commitSignContractTask/{taskId}",method=RequestMethod.POST)
	public void commitSignContractTask(@PathVariable String taskId,@RequestBody SignContractVo params,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitSignContract(params, taskId, sysAccount.getAccountId());
	}
	@RequestMapping(value="/commitLoanCheckTask/{taskId}",method=RequestMethod.POST)
	public void commitLoanCheckTask(@PathVariable String taskId,@RequestBody SignContractVo params,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		taskService.commitLoanCheck(params, taskId, sysAccount.getAccountId());
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
}
