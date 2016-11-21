package com.pujjr.postloan.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.enumeration.EIntervalMode;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.dao.ApplyAlterRepayDateMapper;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.dao.WaitingChargeNewMapper;
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LedgerProcessStatus;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IAlterRepayDateService;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.vo.AlterRepayDateFeeItemVo;
import com.pujjr.postloan.vo.AlterRepayDateTaskVo;
import com.pujjr.postloan.vo.ApplyAlterRepayDateVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.NewRepayPlanVo;
import com.pujjr.postloan.vo.RepayPlanVo;
import com.pujjr.utils.Utils;
@Service
public class AlterRepayDateServiceImpl implements IAlterRepayDateService {

	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private IAccountingService accountingService;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private ApplyAlterRepayDateMapper alterRepayDateDao;
	@Autowired
	private RepayPlanMapper repayPlanDao;
	@Autowired
	private WaitingChargeNewMapper waitingChargeNewDao;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IRunWorkflowService runWorkflowServiceImpl;
	
	@Override
	public AlterRepayDateFeeItemVo getAlterRepayDateFeeItem(String appId, Date oldClosingDate,Date newClosingDate) {
		// TODO Auto-generated method stub
		AlterRepayDateFeeItemVo vo = new AlterRepayDateFeeItemVo();
		
		//变更天数
		int alterDay = new Long(Utils.getTimeInterval(oldClosingDate, newClosingDate, EIntervalMode.DAYS)).intValue();
		vo.setAlterDay(alterDay);
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		DecimalFormat  nf = new DecimalFormat("#.00");
		
		//变更利息
		double alterInterest = Double.valueOf(nf.format(ledgerPo.getRemainCapital()*ledgerPo.getYearRate()/360*(alterDay)));
		vo.setAlterInterest(alterInterest);
		
		/******************************生成新的还款计划************************************/
		List<NewRepayPlanVo> newRepayPlanList = new ArrayList<NewRepayPlanVo>();
		
		//处理当期还款计划，记账日设置为新的结账日，产生的变更利息加在当期上
		RepayPlan curPeriodRepayPlan  = accountingService.getCurrentPeriodRepayPlan(appId);
		NewRepayPlanVo tempCur = new NewRepayPlanVo();
		tempCur.setPeriod(curPeriodRepayPlan.getPeriod());
		tempCur.setRepayCapital(curPeriodRepayPlan.getRepayCapital());
		tempCur.setRepayCnterest(curPeriodRepayPlan.getRepayInterest()+alterInterest);
		tempCur.setRemainCapital(curPeriodRepayPlan.getRemainCapital());
		tempCur.setValueDate(curPeriodRepayPlan.getValueDate());
		tempCur.setClosingDate(newClosingDate);
		tempCur.setRepayTotalAmount(curPeriodRepayPlan.getRepayTotalAmount()+alterInterest);
		newRepayPlanList.add(tempCur);
		
		//处理当期后续还款计划
		List<RepayPlan> nextRepayPlanList = accountingService.getAfterCurrentPeriodRepayPlan(appId, curPeriodRepayPlan.getPeriod());
		int i = 1;
		Date newValueDate = newClosingDate;
		for(RepayPlan item : nextRepayPlanList)
		{
			NewRepayPlanVo tempVo = new NewRepayPlanVo();
			tempVo.setPeriod(item.getPeriod());
			tempVo.setRepayCapital(item.getRepayCapital());
			tempVo.setRepayCnterest(item.getRepayInterest());
			tempVo.setRemainCapital(item.getRemainCapital());
			tempVo.setRepayTotalAmount(item.getRepayTotalAmount());
			tempVo.setValueDate(newValueDate);
			tempVo.setClosingDate(Utils.getDateAfterMonth(newClosingDate, i));
			newValueDate = Utils.getDateAfterMonth(newClosingDate, i);
			i++;
			newRepayPlanList.add(tempVo);
			
		}
		vo.setNewRepayPlanList(newRepayPlanList);
		return vo;
	}

	@Override
	public void commitApplyAlterRepayDateTask(String appId, ApplyAlterRepayDateVo vo,String opreId) {
		// TODO Auto-generated method stub
		
		//修改总账处理状态为申请还款日变更,避免其他交易操作
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		ledgerPo.setProcessStatus(LedgerProcessStatus.ApplyAlterRepayDate.getName());
		ledgerDao.updateByPrimaryKey(ledgerPo);
		
		//创建申请信息
		ApplyAlterRepayDate po = new ApplyAlterRepayDate();
		String businessKey = Utils.get16UUID();
		po.setId(businessKey);
		po.setAppId(appId);
		po.setOldClosingDate(vo.getOldClosingDate());
		po.setNewClosingDate(vo.getNewClosingDate());
		po.setAlterDay(vo.getFeeItem().getAlterDay());
		po.setAlterInterest(vo.getFeeItem().getAlterInterest());
		po.setApplyComment(vo.getApplyComment());
		po.setApplyStatus(LoanApplyStatus.Approving.getName());
		po.setApplyEffectDate(vo.getApplyEffectDate());
		po.setCreateId(opreId);
		po.setCreateDate(new Date());
		
		//保存新的还款计划
		for(NewRepayPlanVo item : vo.getFeeItem().getNewRepayPlanList())
		{
			WaitingChargeNew newPlan = new WaitingChargeNew();
			newPlan.setId(Utils.get16UUID());
			newPlan.setApplyType(LoanApplyTaskType.AlterRepayDate.getName());
			newPlan.setApplyRefId(businessKey);
			newPlan.setAppId(appId);
			newPlan.setFeeType(FeeType.Plan.getName());
			//获取对应还款计划
			RepayPlan oldPlan = repayPlanDao.selectRepayPlan(appId, item.getPeriod());
			newPlan.setFeeRefId(oldPlan.getId());
			newPlan.setPeriod(item.getPeriod());
			newPlan.setDoSettle(false);
			newPlan.setRepayCapital(item.getRepayCapital());
			newPlan.setRepayInterest(item.getRepayCnterest());
			newPlan.setRepayOverdueAmount(0.00);
			newPlan.setValueDate(item.getValueDate());
			newPlan.setClosingDate(item.getClosingDate());
			newPlan.setRemainCapitao(item.getRemainCapital());
			newPlan.setAddupOverdueAmount(0.00);
			newPlan.setAddupOverdueDay(0);
			waitingChargeNewDao.insert(newPlan);
		}
		
		//启动流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, opreId);
		ProcessInstance instance = runWorkflowService.startProcess("HKRBG", businessKey, vars);
		po.setProcInstId(instance.getId());
		alterRepayDateDao.insert(po);
	}

	@Override
	public void commitApproveAlterRepayDateTask(String taskId, ApproveResultVo vo) throws Exception {
		// TODO Auto-generated method stub
		//1、检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),task.getTaskDefinitionKey());
		if (runpath == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		//2、获取申请数据
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplyAlterRepayDate po = alterRepayDateDao.selectByPrimaryKey(businessKey);
		
		//3、保存任务处理结果信息
		String procId = Utils.get16UUID();
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(procId);
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(vo.getApproveResult());
		taskProcessResult.setComment(vo.getApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
		
		//4、根据审批结果执行相关操作
		if(vo.getApproveResult().equals(TaskCommitType.LOAN_PASS))
		{
			po.setApplyStatus(LoanApplyStatus.ApprovePass.getName());
		}
		else
		{
			po.setApplyStatus(LoanApplyStatus.ApproveRefuse.getName());
			//如果失败流程结束则释放总账状态
			GeneralLedger ledgerPo = ledgerDao.selectByAppId(po.getAppId());
			ledgerPo.setProcessStatus("");
			ledgerDao.updateByPrimaryKey(ledgerPo);
		}
		alterRepayDateDao.updateByPrimaryKey(po);
		
		//5、提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("approveProcessResult", vo.getApproveResult());
		runWorkflowServiceImpl.completeTask(taskId, "", vars, CommandType.COMMIT);
	}


	@Override
	public void commitConfirmAlterRepayDateTask(String taskId) throws Exception {
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
		ApplyAlterRepayDate po = alterRepayDateDao.selectByPrimaryKey(businessKey);
		po.setApplyStatus(LoanApplyStatus.Confirmed.getName());
		
		//刷新新的变更后还款计划
		accountingService.repayReverseAccountingUserNewWaitingChargeTable(po.getId(), LoanApplyTaskType.AlterRepayDate, po.getAppId());
		
		//释放总账状态
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(po.getAppId());
		ledgerPo.setProcessStatus("");
		ledgerDao.updateByPrimaryKey(ledgerPo);
		
		//提交任务
		runWorkflowServiceImpl.completeTask(taskId, "", null, CommandType.COMMIT);
	}

	@Override
	public void cancelAlterRepayDateTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AlterRepayDateTaskVo> getApplyAlterRepayDateTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return alterRepayDateDao.selectApplyAlterRepayDateTaskList(createId, applyStatus);
	}

	@Override
	public ApplyAlterRepayDate getApplyAlterRepayDateTaskById(String id) {
		// TODO Auto-generated method stub
		return alterRepayDateDao.selectByPrimaryKey(id);
	}

	@Override
	public void modifyApplyAlterRepayDateInfo(ApplyAlterRepayDate record) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteApplyAlterRepayDateInfoById(String id) {
		// TODO Auto-generated method stub

	}

}
