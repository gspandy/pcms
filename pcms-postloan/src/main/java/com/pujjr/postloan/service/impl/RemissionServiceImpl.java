package com.pujjr.postloan.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.RuleRemission;
import com.pujjr.base.service.IRuleService;
import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.dao.ApplyRemissionMapper;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.RemissionItemMapper;
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.ApplyRefund;
import com.pujjr.postloan.domain.ApplyRemission;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.RemissionItem;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LedgerProcessStatus;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IRemissionService;
import com.pujjr.postloan.vo.ApplyRemissionVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.NewRepayPlanVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RemissionTaskVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.utils.Utils;
@Service
public class RemissionServiceImpl implements IRemissionService 
{

	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private IAccountingService accountingService;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private RemissionItemMapper remissionItemDao;
	@Autowired
	private ApplyRemissionMapper applyRemissionDao;
	@Autowired
	private IRuleService ruleService;
	
	@Override
	public RepayFeeItemVo getRemissionRepayFeeItem(String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commitApplyRemissionTask(String appId, ApplyRemissionVo vo,String operId) {
		// TODO Auto-generated method stub
		//修改总账处理状态为申请减免,避免其他交易操作
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		ledgerPo.setProcessStatus(LedgerProcessStatus.ApplyRemission.getName());
		ledgerDao.updateByPrimaryKey(ledgerPo);
		
		//创建申请信息
		ApplyRemission po = new ApplyRemission();
		String businessKey = Utils.get16UUID();
		po.setId(businessKey);
		po.setAppId(appId);
		po.setRemissionDate(vo.getRemissionDate());
		po.setApplyComment(vo.getApplyComment());
		po.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		po.setCreateDate(new Date());
		po.setCreateId(operId);
		
		//保存减免费项
		RemissionItem remissionItemPo = new RemissionItem();
		remissionItemPo.setId(Utils.get16UUID());
		remissionItemPo.setApplyType(LoanApplyTaskType.Remission.getName());
		remissionItemPo.setApplyRefId(businessKey);
		remissionItemPo.setCapital(vo.getRemissionFeeItemVo().getCapital());
		remissionItemPo.setInterest(vo.getRemissionFeeItemVo().getInterest());
		remissionItemPo.setOverdueAmount(vo.getRemissionFeeItemVo().getOverdueAmount());
		remissionItemPo.setOtherFee(vo.getRemissionFeeItemVo().getOtherFee());
		remissionItemPo.setOtherOverdueAmount(vo.getRemissionFeeItemVo().getOtherOverdueAmount());
		remissionItemPo.setLateFee(0.00);
		remissionItemDao.insert(remissionItemPo);
		
		//启动流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("JIANMIAN", businessKey, vars);
		po.setProcInstId(instance.getProcessInstanceId());
		applyRemissionDao.insert(po);		
	}

	@Override
	public void commitApproveRemissionTask(String taskId, ApproveResultVo vo) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),task.getTaskDefinitionKey());
		if (runpath == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		//获取申请数据
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplyRemission po = applyRemissionDao.selectByPrimaryKey(businessKey);
		
		//保存任务处理结果信息
		String procId = Utils.get16UUID();
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(procId);
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(vo.getApproveResult());
		taskProcessResult.setComment(vo.getApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
				
		//判断审批结果，并根据是否超过自身减免额度，如超过则由下一级来决定，如已经是最后一级则直接作出最终结果
		String taskDefKey = task.getTaskDefinitionKey();
		//通过
		if(vo.getApproveResult().equals(TaskCommitType.LOAN_PASS))
		{
			//获取减免费项
			RemissionItem remissionItemPo = remissionItemDao.selectByApplyId(LoanApplyTaskType.Remission.getName(), businessKey);
			double capital = remissionItemPo.getCapital();
			double interest = remissionItemPo.getInterest();
			double overdueAmt = remissionItemPo.getOverdueAmount();
			double otherFee = remissionItemPo.getOtherFee();
			double otherOverdueAmt = remissionItemPo.getOtherOverdueAmount();
			double totalOverdueAmt = overdueAmt+otherOverdueAmt;
			double lateFee = remissionItemPo.getLateFee();
			//获取任务执行人组ID
			String batchAssigneeWorkgroupId = runtimeService.getVariable(task.getExecutionId(), "batchAssigneeWorkgroupId").toString();
			//获取组减免规则
			RuleRemission rule = ruleService.getWorkgroupRemissionRule(batchAssigneeWorkgroupId);
			//当不为四级审批并且任何一项减免费用超过组设定则任务需要升级处理
			if(
					(Double.compare(capital, rule.getMaxCapital())>0 ||
					Double.compare(interest, rule.getMaxInterest())>0 ||
					Double.compare(otherFee, rule.getMaxOtherFee())>0 ||
					Double.compare(lateFee, rule.getMaxLateFee())>0 ||
					Double.compare(totalOverdueAmt, rule.getMaxOverdueAmt())>0) &&  !taskDefKey.equals("shjjmsp")
			  )
			{
				runtimeService.setVariable(task.getExecutionId(), "needUpgradeApprove", true);
			}
			else
			{
				RemissionFeeItemVo remissionItem = new RemissionFeeItemVo();
				double totalRemissionAmt = 0.00;
				if(Double.compare(otherOverdueAmt, 0.00)>0)
				{
					remissionItem.setOtherOverdueAmount(otherOverdueAmt);
					totalRemissionAmt += otherOverdueAmt;
				}
				if(Double.compare(otherFee, 0.00)>0)
				{
					remissionItem.setOtherFee(otherFee);
					totalRemissionAmt+=otherFee;
				}
				if(Double.compare(overdueAmt, 0.00)>0)
				{
					remissionItem.setOverdueAmount(overdueAmt);
					totalRemissionAmt+=overdueAmt;
				}
				if(Double.compare(interest, 0.00)>0)
				{
					remissionItem.setInterest(interest);
					totalRemissionAmt+=interest;
				}
				if(Double.compare(capital, 0.00)>0)
				{
					remissionItem.setCapital(capital);
					totalRemissionAmt+=capital;
				}
				accountingService.repayReverseAccounting(po.getAppId(), totalRemissionAmt, po.getRemissionDate(), RepayMode.Remission, remissionItem);
				runtimeService.setVariable(task.getExecutionId(), "needUpgradeApprove", false);
				po.setApplyStatus(LoanApplyStatus.ApprovePass.getName());
				//最终审批通过则释放总账状态
				GeneralLedger ledgerPo = ledgerDao.selectByAppId(po.getAppId());
				ledgerPo.setProcessStatus("");
				ledgerDao.updateByPrimaryKey(ledgerPo);
			}
		}
		else
		{
			runtimeService.setVariable(task.getExecutionId(), "needUpgradeApprove", false);
			po.setApplyStatus(LoanApplyStatus.ApproveRefuse.getName());
			//如果失败流程结束则释放总账状态
			GeneralLedger ledgerPo = ledgerDao.selectByAppId(po.getAppId());
			ledgerPo.setProcessStatus("");
			ledgerDao.updateByPrimaryKey(ledgerPo);
		}
		applyRemissionDao.updateByPrimaryKey(po);
		
		//提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		runWorkflowService.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void cancelRemissionTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RemissionTaskVo> getApplyRemissionTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return applyRemissionDao.selectApplyRemissionTaskList(createId, applyStatus);
	}

	@Override
	public ApplyRemission getApplyRemissionTaskById(String id) {
		// TODO Auto-generated method stub
		return applyRemissionDao.selectByPrimaryKey(id);
	}

	@Override
	public void modifyApplyRemissionInfo(ApplyRemission record) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteApplyRemissionInfoById(String id) {
		// TODO Auto-generated method stub

	}

}
