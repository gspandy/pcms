package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.dao.SysWorkgroupMapper;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.dao.CheckResultMapper;
import com.pujjr.carcredit.dao.ReconsiderMapper;
import com.pujjr.carcredit.dao.TaskMapper;
import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.CheckResult;
import com.pujjr.carcredit.domain.Reconsider;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyApproveVo;
import com.pujjr.carcredit.vo.ApplyCheckVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.ReconsiderApplyVo;
import com.pujjr.carcredit.vo.ReconsiderApproveVo;
import com.pujjr.carcredit.vo.SignCommitType;
import com.pujjr.carcredit.vo.SignContractVo;
import com.pujjr.carcredit.vo.SignFinanceDetailVo;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.carcredit.vo.TaskLoanApproveVo;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class) 
public class TaskServiceImpl implements ITaskService 
{
	@Autowired
	private TaskMapper taskDao;
	@Autowired
	private IRunWorkflowService  runWorkflowService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private CheckResultMapper checkResultDao;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	@Autowired
	private ISysWorkgroupService workgroupService;
	@Autowired
	private ISignContractService signContractService;
	@Autowired
	private ReconsiderMapper reconsiderDao;
	
	public List<ToDoTaskPo> getToDoTaskListByAccountId(String accountId,String queryType) {
		// TODO Auto-generated method stub
		return taskDao.selectToDoTaskListByAccountId(accountId,queryType);
	}

	public void commitApplyTask(ApplyVo applyVo, String operId) throws Exception {
		// TODO Auto-generated method stub
		String businessKey = applyService.saveApply(applyVo, operId);
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("productCode", applyVo.getProduct().getProductCode());
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		vars.put("assigneeType", "1");
		runWorkflowService.startProcess("PCCA", businessKey, vars);
	}
	
	public void commitCheckTask(ApplyVo applyVo,ApplyCheckVo checkVo, String taskId,String operId) throws Exception {
		// TODO Auto-generated method stub
		//1、保存申请单变更信息
		applyService.saveApply(applyVo, operId);
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
		
		//2、保存审核信息
		CheckResult checkResult = new CheckResult();
		String taskBusinessId = Utils.get16UUID();
		checkResult.setId(taskBusinessId);
		checkResult.setAppId(applyVo.getAppId());
		checkResult.setNetCheckResult(checkVo.getNetCheckResult());
		checkResult.setNetCheckNotPassReason(checkVo.getNetCheckNotPassReason());
		checkResult.setNetCheckComment(checkVo.getNetCheckComment());
		checkResult.setTelCheckResult(checkVo.getTelCheckResult());
		checkResult.setTelCheckNotPassReason(checkVo.getTelCheckNotPassReason());
		checkResult.setTelCheckComment(checkVo.getTelCheckComment());
		checkResultDao.insert(checkResult);
		
		//3、保存任务处理结果信息
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(Utils.get16UUID());
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(checkVo.getResult());
		//建议通过
		if(checkVo.getResult().equals(TaskCommitType.SUGGEST_PASS))
		{
			taskProcessResult.setProcessResultDesc("建议通过");
		}
		else if(checkVo.getResult().equals(TaskCommitType.SUGGEST_CONDITION_LOAN))
		{
			taskProcessResult.setProcessResultDesc(checkVo.getLoanExtConditon());
		}
		else if(checkVo.getResult().equals(TaskCommitType.SUGGEST_CANCEL))
		{
			taskProcessResult.setProcessResultDesc(checkVo.getCancelReason());
		}
		else
		{
			taskProcessResult.setProcessResultDesc(checkVo.getRejectReason());
		}
		taskProcessResult.setComment(checkVo.getComment());
		taskProcessResult.setTaskBusinessId(taskBusinessId);
		taskProcessResultDao.insert(taskProcessResult);
		
		//4、放入流程变量
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("checkProcessResult", checkVo.getResult());
		
		runWorkflowService.completeTask(taskId, "提交任务",vars, CommandType.COMMIT);
	}

	public void commitApproveTask(ApplyVo applyVo, ApplyApproveVo approveVo, String taskId, String operId) throws Exception {
		// TODO Auto-generated method stub
		// 1、保存申请单变更信息
		applyService.saveApply(applyVo, operId);
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),
				task.getTaskDefinitionKey());
		if (runpath == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}

		// 2、保存任务处理结果信息
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(Utils.get16UUID());
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(approveVo.getResult());
		// 建议通过
		if (approveVo.getResult().equals(TaskCommitType.PASS)) {
			taskProcessResult.setProcessResultDesc("通过");
		} else if (approveVo.getResult().equals(TaskCommitType.CONDITION_LOAN)) {
			taskProcessResult.setProcessResultDesc(approveVo.getLoanExtConditon());
		} else if (approveVo.getResult().equals(TaskCommitType.CANCEL)) {
			taskProcessResult.setProcessResultDesc(approveVo.getCancelReason());
		} else {
			taskProcessResult.setProcessResultDesc(approveVo.getRejectReason());
		}
		taskProcessResult.setComment(approveVo.getComment());
		taskProcessResult.setTaskBusinessId(Utils.get16UUID());
		taskProcessResultDao.insert(taskProcessResult);

		// 4、放入流程变量
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("approveProcessResult", approveVo.getResult());

		runWorkflowService.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
	}

	@Override
	public String getMinTaskCountAccountIdByWorkgroupId(String workgroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessTaskUserBo getProcessTaskAccount(String productCode, double financeAmount, String dealerId,String workgroupId,List<String> candidateAccounts) {
		// TODO Auto-generated method stub
		ProcessTaskUserBo bo = new ProcessTaskUserBo();
		//1、获取工作组及其子工作组
		List<SysWorkgroup> listGroup = getChildWorkgroup(workgroupId,true);
		//2、获取满足任务的用户，如果有待选用户则从待选中过滤用户
		List<HashMap> listMatch = workgroupService.getMatchRuleAccountList(productCode, financeAmount, dealerId, listGroup,candidateAccounts);
		if(listMatch.size()==0)
		{
			return null;
		}
		else
		{
			//3、获取满足执行条件用户当前任务数及可支配最大任务数
			List<String> listMatchRuleTaskCntId = new ArrayList<String>();
			String assignee=null;
			String assigneeWorkgroupId = null;
			int hasRemainCnt = 0;
			for(HashMap item :listMatch)
			{
				listMatchRuleTaskCntId.add(item.get("taskCntRuleId").toString());
			}
			List<HashMap> matchAccountTaskCntInfo = taskDao.selectTaskAssignCntInfo(listMatchRuleTaskCntId);
			for(HashMap l : matchAccountTaskCntInfo)
			{
				int curTaskCnt = Integer.valueOf(l.get("curTaskCnt").toString());
				int maxTaskCnt = Integer.valueOf(l.get("maxTaskCnt").toString());
				if((maxTaskCnt-curTaskCnt)>hasRemainCnt)
				{
					hasRemainCnt = maxTaskCnt-curTaskCnt;
					assignee = l.get("assignee").toString();
					assigneeWorkgroupId = l.get("workgroupId").toString();
				}
				
			}
			if(assignee == null)
			{
				return null;
			}
			else
			{
				bo.setAccountId(assignee);
				bo.setWorkgroupId(assigneeWorkgroupId);
			}
			return bo;
		}
	}
	
	private List<SysWorkgroup> getChildWorkgroup(String workgroupId,boolean includeSelf )
	{
		List<SysWorkgroup>  list = new ArrayList<SysWorkgroup>();
		SysWorkgroup parentgroup = workgroupService.getWorkgroupById(workgroupId);
		if(includeSelf)
		{
			list.add(parentgroup);
		}
		List<SysWorkgroup> childGroups = workgroupService.getSysWorkgroupListByParentId(parentgroup.getId());
		if(childGroups.size()>0)
		{
			for(SysWorkgroup childGroup : childGroups)
			{
				list.addAll(getChildWorkgroup(childGroup.getId(),true));
			}
		}
		return list;
	}

	@Override
	public List<OnlineAcctPo> getOnlineAcctInfo(String workgroupId) {
		// TODO Auto-generated method stub
		List<OnlineAcctPo> poList = new ArrayList<OnlineAcctPo>();
		List<SysWorkgroup> listGroup = getChildWorkgroup(workgroupId,true);
		List<HashMap> listMatch = workgroupService.getWorkgroupOnlineAccountList(listGroup);
		if(listMatch.size()==0)
		{
			return null;
		}
		else
		{
			//3、获取满足执行条件用户当前任务数及可支配最大任务数
			List<String> listMatchAccountId = new ArrayList<String>();
			for(HashMap item :listMatch)
			{
				listMatchAccountId.add(item.get("taskCntRuleId").toString());
			}
			List<HashMap> matchAccountTaskCntInfo = taskDao.selectTaskAssignCntInfo(listMatchAccountId);
			for(HashMap l : matchAccountTaskCntInfo)
			{
				int curTaskCnt = Integer.valueOf(l.get("curTaskCnt").toString());
				int maxTaskCnt = Integer.valueOf(l.get("maxTaskCnt").toString());
				String assignee = l.get("assignee").toString();
				String assigneeName = l.get("accountName").toString();
				OnlineAcctPo po = new OnlineAcctPo();
				po.setAccountId(assignee);
				po.setCurTaskCnt(curTaskCnt);
				po.setMaxTaxkCnt(maxTaskCnt);
				po.setAccountName(assigneeName);
				poList.add(po);
			}
		}
		return poList;
	}

	@Override
	public void commitSignContract(SignContractVo signContractVo,String taskId,String operId) {
		// TODO Auto-generated method stub
		String appId = signContractVo.getAppId();
		SignContract signContractPo = new SignContract();
		BeanUtils.copyProperties(signContractVo, signContractPo);
		if(signContractService.getSignContractByAppId(signContractVo.getAppId())==null)
		{
			//如果没有签约信息则创建签约信息及合同编号
			signContractPo.setId(Utils.get16UUID());
			signContractPo.setContractNo("ContractNo"+appId);
			signContractPo.setCreateId(operId);
			signContractPo.setCreateTime(new Date());
			signContractService.addSignContract(signContractPo);
		}
		else
		{
			signContractPo.setUpdateId(operId);
			signContractPo.setUpdateTime(new Date());
			signContractService.modifySignContract(signContractPo);
		}
		
		
		//删除签约融资明细信息后再插入
		signContractService.deleteSignFinanceDetailByAppId(appId);
		for(SignFinanceDetailVo item : signContractVo.getSignFinanceList())
		{
			SignFinanceDetail detailPo = new SignFinanceDetail();
			BeanUtils.copyProperties(item.getSignFinanceDetail(), detailPo);
			detailPo.setId(Utils.get16UUID());
			detailPo.setAppId(appId);
			detailPo.setFinanceId(item.getApplyFinance().getId());
			signContractService.addSignFinanceDetail(detailPo);
		}
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("signType", SignCommitType.SIGN);
		runWorkflowService.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
	}

	@Override
	public void commitLoanCheck(SignContractVo signContractVo, String taskId, String operId) {
		// TODO Auto-generated method stub
		for(SignFinanceDetailVo item : signContractVo.getSignFinanceList())
		{
			SignFinanceDetail detailPo = new SignFinanceDetail();
			BeanUtils.copyProperties(item.getSignFinanceDetail(), detailPo);
			//这里只更新放款复核信息
			signContractService.modifySignFinanceDetail(detailPo);
		}
		runWorkflowService.completeTask(taskId, "提交任务", null, CommandType.COMMIT);
	}

	@Override
	public void commitPrevLoanApprove(String taskId, String operId) {
		// TODO Auto-generated method stub
		runWorkflowService.completeTask(taskId, "提交任务", null, CommandType.COMMIT);
	}

	@Override
	public void commitLoanApprove(TaskLoanApproveVo loanApproveVo, String taskId, String operId) throws Exception {
		// TODO Auto-generated method stub
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
		
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(Utils.get16UUID());
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(loanApproveVo.getLoanApproveResult());
		taskProcessResult.setComment(loanApproveVo.getLoanApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
		
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("loanApproveProcessResult", loanApproveVo.getLoanApproveResult());
		runWorkflowService.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
	}

	@Override
	public TaskProcessResult getTaskProcessResultByPathId(String pathId) {
		// TODO Auto-generated method stub
		return taskProcessResultDao.selectByRunPathId(pathId);
	}

	@Override
	public void commitReconsiderApply(ReconsiderApplyVo reconsiderApplyVo, String appId,String taskId, String operId) {
		// TODO Auto-generated method stub
		reconsiderDao.disabledAllByAppId(appId);
		Reconsider po = new Reconsider();
		po.setId(Utils.get16UUID());
		po.setAppId(appId);
		po.setEnabled(true);
		po.setRejectReason(reconsiderApplyVo.getRejectReason());
		po.setRecommitReason(reconsiderApplyVo.getRecommitReason());
		po.setRecommitComment(reconsiderApplyVo.getRecommitComment());
		reconsiderDao.insert(po);
		runWorkflowService.completeTask(taskId, "提交任务", null, CommandType.COMMIT);
	}

	@Override
	public Reconsider getEnabledReconsiderByAppId(String appId) {
		// TODO Auto-generated method stub
		return reconsiderDao.selectEnabledByAppId(appId);
	}

	@Override
	public void commitReconsiderApprove(ReconsiderApproveVo reconsiderApproveVo, String taskId, String operId) throws Exception {
		// TODO Auto-generated method stub
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),
				task.getTaskDefinitionKey());
		if (runpath == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}

		// 2、保存任务处理结果信息
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(Utils.get16UUID());
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(reconsiderApproveVo.getApproveResult());
		// 建议通过
		if (reconsiderApproveVo.getApproveResult().equals(TaskCommitType.RECONSIDER_PASS)) {
			taskProcessResult.setProcessResultDesc("通过");
		} else {
			taskProcessResult.setProcessResultDesc("拒绝");
		}
		taskProcessResult.setComment(reconsiderApproveVo.getApproveComment());
		taskProcessResult.setTaskBusinessId(Utils.get16UUID());
		taskProcessResultDao.insert(taskProcessResult);
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("reconsiderApproveProcessResult", reconsiderApproveVo.getApproveResult());
		runWorkflowService.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
	}

	@Override
	public void backTask(String taskId, String message) {
		// TODO Auto-generated method stub
		runWorkflowService.completeTask(taskId, message, null, CommandType.BACK);
	}


}
