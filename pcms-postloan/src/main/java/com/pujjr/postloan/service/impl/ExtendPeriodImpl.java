package com.pujjr.postloan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.pujjr.base.domain.RuleRemission;
import com.pujjr.base.service.IRuleService;
import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.enumeration.EIntervalMode;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.dao.ApplyExtendPeriodMapper;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.OtherFeeMapper;
import com.pujjr.postloan.dao.RemissionItemMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.dao.WaitingChargeNewMapper;
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.ApplyExtendPeriod;
import com.pujjr.postloan.domain.ApplyRemission;
import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.domain.RemissionItem;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.enumeration.ERemissionType;
import com.pujjr.postloan.enumeration.ETaskTag;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LedgerProcessStatus;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IExtendPeriodService;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.vo.ApplyExtendPeriodVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.ExtendPeriodFeeItemVo;
import com.pujjr.postloan.vo.ExtendPeriodTaskVo;
import com.pujjr.postloan.vo.NewRepayPlanVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.postloan.vo.RepayPlanVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class ExtendPeriodImpl implements IExtendPeriodService {
	private Logger logger = Logger.getLogger(ExtendPeriodImpl.class);
	@Autowired
	private IAccountingService accountingServiceImpl;
	@Autowired
	private WaitingChargeMapper waitingChargeMapper;
	@Autowired
	private GeneralLedgerMapper generalLedgerMapper;
	@Autowired
	private IPlanService planServiceImpl;
	@Autowired
	private IRunWorkflowService runWorkflowServiceImpl;
	@Autowired
	private ApplyExtendPeriodMapper applyExtendPeriodMapper;
	@Autowired
	private WaitingChargeNewMapper waitingChargeNewMapper;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	@Autowired
	private RemissionItemMapper remissionItemMapper;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IRuleService ruleService;
	@Autowired
	private RepayPlanMapper repayPlanMapper;
	@Autowired
	private OtherFeeMapper otherFeeMapper;
	@Override
	public long getLastDateInterval(Date currDate,Date lastRepayDate,int currPeriod,int lastPeriod,int extendPeriod){
		int newEndPeriod = lastPeriod + extendPeriod;
		Date nextValueDate = Utils.getDateAfterMonth(currDate, 1);//上一期结账日=下一期起息日（依据1.0系统设计思想）
		int nextPeriod = currPeriod + 1;
		Date newLastValueDate = Utils.getDateAfterMonth(nextValueDate, newEndPeriod - nextPeriod);
		Date newlastClosingDate = Utils.getDateAfterMonth(newLastValueDate, 1);
		return Utils.getTimeInterval(lastRepayDate, newlastClosingDate, EIntervalMode.DAYS);
	}
	
	@Override
	public ExtendPeriodFeeItemVo getExtendPeriodFeeItem(String appId,int extendPeriod) {
		ExtendPeriodFeeItemVo ev = new ExtendPeriodFeeItemVo();
		Date currDate =	Utils.getDate();
		//代扣明细表：计划还款
		List<WaitingCharge> waitingChargePlanList = accountingServiceImpl.getWaitingChargeTypePlan(appId, false);
		//代扣明细表：其他费用
		List<WaitingCharge> waitingChargesOtherList = accountingServiceImpl.getWaitingChargeTypeOther(appId);
		RepayPlan currRepayPlan = accountingServiceImpl.getCurrentPeriodRepayPlan(appId);
		RepayPlan lastRepayPlan = accountingServiceImpl.getLatestPeriodRepayPlan(appId);
		GeneralLedger generalledger = generalLedgerMapper.selectByAppId(appId);//总账表
		double monthRate = generalledger.getYearRate()/12;//月利率
		double dayRate = generalledger.getYearRate()/360;//日利率
		double extendRate = generalledger.getDayExtendRate();//日展期费率
		double lateRate = generalledger.getDayLateRate();//日罚息率
		//代扣明细表：计划还款汇总
		double overDueCapital = 0.00;
		double overDueInterest = 0.00;
		double overDueFine = 0.00;
		for (WaitingCharge waitingCharge : waitingChargePlanList) {
			overDueCapital += waitingCharge.getRepayCapital();
			overDueInterest += waitingCharge.getRepayInterest();
			overDueFine += waitingCharge.getRepayOverdueAmount();
		}
		//当期
		double currCaptital = currRepayPlan.getRepayCapital();
		double currInterest = currRepayPlan.getRepayInterest();
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, true, currDate, false, true);
		double remainCapital = repayFeeItemVo.getRemainCapital();//剩余本金
		int currPeriod = currRepayPlan.getPeriod();
		//代扣明细表：其他费用汇总
		double otherCapital = 0.00;
		double otherInterest = 0.00;
		double otherFine = 0.00;
		for (WaitingCharge waitingCharge : waitingChargesOtherList) {
			otherCapital += waitingCharge.getRepayCapital();
			otherFine += waitingCharge.getRepayOverdueAmount();
		}
		//展期费(当前期数期初本金余额（当期所有剩余本金-逾期本金）  * 展期率 * 展期天数)
		double extendFee = (remainCapital - overDueCapital) * extendRate * this.getLastDateInterval(currDate, lastRepayPlan.getClosingDate(), currPeriod, lastRepayPlan.getPeriod(), extendPeriod);
		//新旧还款利息（当前期数期初本金余额（当期所有剩余本金-逾期本金）  * 日利息* （展期后新起息日-当前期数起息日））
		double newOldInterest = (remainCapital - overDueCapital) * dayRate * Utils.getTimeInterval(currRepayPlan.getValueDate(), currDate, EIntervalMode.DAYS);
		//新计息本金(其他费用本金+其他费用罚息+逾期本金+逾期罚息+当期利息+新旧还款日利息+当前剩余本金)
		double newCapital = otherCapital + otherFine 
				+ overDueInterest + overDueFine 
				+ currInterest
				+ newOldInterest
				+ remainCapital;
		//新还款计划
		int newPeriodCnt = lastRepayPlan.getPeriod() - (currPeriod - 1) + extendPeriod;//新生成还款计划期数(展期期数：当期至原还款计划最后一期+展期期数)
		List<RepayPlan> repayPlanList = planServiceImpl.selectRefreshRepayPlanListExtendPeriod(appId, newCapital, monthRate, newPeriodCnt, currDate, EInterestMode.CPM, currPeriod - 1);//currPeriod - 1：展期过程中，当前周期同样参与展期，展期后，新还款计划还款周期数从当前周期开始编码
		
		ev.setNewCapital(newCapital);
		ev.setNewOldInterest(newOldInterest);
		ev.setExtendFee(extendFee);
		
//		ev.setOtherAmount(otherCapital + otherInterest + otherFine);
		ev.setOtherAmount(repayFeeItemVo.getOtherAmount());
//		ev.setOtherOverdueAmount(otherFine);
		ev.setOtherOverdueAmount(repayFeeItemVo.getOtherOverdueAmount());
		
		ev.setRemainCapital(remainCapital);
		ev.setRepayCapital(repayFeeItemVo.getRepayCapital());
		ev.setRepayInterest(repayFeeItemVo.getRepayInterest());
		ev.setRepayOverdueAmount (repayFeeItemVo.getRepayOverdueAmount());
		double stayAmount = accountingServiceImpl.getStayAmount(appId);//挂账金额
		ev.setStayAmount(stayAmount);
		ev.setRepayPlanList(repayPlanList);
//		System.out.println("JSONObject.toJSONString(ev):"+JSONObject.toJSONString(ev));
		
		//double数据格式化
		Utils.formateDoubleOfObject(ev, 2);
		for (RepayPlan repayPlan : ev.getRepayPlanList()) {
			Utils.formateDoubleOfObject(repayPlan, 2);
		}
		logger.debug("ev:"+ev);
		return ev;
	}

	@Override
	public void commitApplyExtendPeriodTask(String operId,String appId, ApplyExtendPeriodVo vo) throws Exception {
		
		accountingServiceImpl.checkCandoExtendPeriod(appId);
		//修改总账处理状态为申请展期,避免其他交易操作
		GeneralLedger ledgerPo = generalLedgerMapper.selectByAppId(appId);
		ledgerPo.setProcessStatus(LedgerProcessStatus.ApplyExtendPeriod.getName());
		generalLedgerMapper.updateByPrimaryKey(ledgerPo);
				
		//1、记录入申请表
		ApplyExtendPeriod applyExtendPeriod = new ApplyExtendPeriod();
		String businessKey = Utils.get16UUID();
		ExtendPeriodFeeItemVo feeItem = vo.getFeeItem();
		applyExtendPeriod.setId(businessKey);
		applyExtendPeriod.setAppId(appId);
		applyExtendPeriod.setRepayCapital(feeItem.getRepayCapital());
		applyExtendPeriod.setRepayInterest(feeItem.getRepayInterest());
		applyExtendPeriod.setRepayOverdueAmount(feeItem.getRepayOverdueAmount());
		applyExtendPeriod.setOtherFee(feeItem.getOtherAmount());
		applyExtendPeriod.setOtherOverdueAmount(feeItem.getOtherOverdueAmount());
		applyExtendPeriod.setExtendPeriod(vo.getExtendPeriod());
		applyExtendPeriod.setExtendFee(feeItem.getExtendFee());
		applyExtendPeriod.setNewOldInterest(feeItem.getNewOldInterest());
		applyExtendPeriod.setOldRepayMode(vo.getOldRepayMode());
		applyExtendPeriod.setNewRepayMode(vo.getNewRepayMode());
		applyExtendPeriod.setRemainCapital(feeItem.getRemainCapital());
		applyExtendPeriod.setNewCapital(feeItem.getNewCapital());
		applyExtendPeriod.setApplyComment(vo.getApplyComment());
		applyExtendPeriod.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		applyExtendPeriod.setApplyEndDate(vo.getApplyEndDate());
		applyExtendPeriod.setCreateId(operId);
		applyExtendPeriod.setCreateDate(Utils.getDate());
		applyExtendPeriodMapper.insert(applyExtendPeriod);
		
		//2、待扣明细表逾期计划待扣款、其他费用待扣款----》新待扣明细表
		List<WaitingCharge> waitingChargeListPlan = accountingServiceImpl.getWaitingChargeTypePlan(appId, false);
		List<WaitingCharge> waitingChargeListOther = accountingServiceImpl.getWaitingChargeTypeOther(appId);
		for (WaitingCharge waitingCharge : waitingChargeListPlan) {
			WaitingChargeNew waitingChargeNew = new WaitingChargeNew();
			waitingChargeNew.setId(Utils.get16UUID());
			waitingChargeNew.setApplyType(LoanApplyTaskType.ExtendPeriod.getName());
			waitingChargeNew.setApplyRefId(businessKey);
			waitingChargeNew.setAppId(appId);
			waitingChargeNew.setFeeType(FeeType.Plan.getName());
			waitingChargeNew.setFeeRefId(waitingCharge.getFeeRefId());
			waitingChargeNew.setDoSettle(true);
			waitingChargeNew.setRepayCapital(0.00);
			waitingChargeNew.setRepayInterest(0.00);
			waitingChargeNew.setRepayOverdueAmount(0.00);
			RepayPlan repayPlan = repayPlanMapper.selectByPrimaryKey(waitingCharge.getFeeRefId());
			waitingChargeNew.setValueDate(repayPlan.getValueDate());
			waitingChargeNew.setClosingDate(repayPlan.getClosingDate());
			waitingChargeNew.setAddupOverdueAmount(0.00);
			waitingChargeNew.setAddupOverdueDay(repayPlan.getAddupOverdueDay());
			waitingChargeNewMapper.insert(waitingChargeNew);
		}
		for (WaitingCharge waitingCharge : waitingChargeListOther) {
			WaitingChargeNew waitingChargeNew = new WaitingChargeNew();
			waitingChargeNew.setId(Utils.get16UUID());
			waitingChargeNew.setApplyType(LoanApplyTaskType.ExtendPeriod.getName());
			waitingChargeNew.setApplyRefId(businessKey);
			waitingChargeNew.setAppId(appId);
			waitingChargeNew.setFeeType(FeeType.Other.getName());
			waitingChargeNew.setFeeRefId(waitingCharge.getFeeRefId());
			waitingChargeNew.setDoSettle(true);
			waitingChargeNew.setRepayCapital(0.00);
			waitingChargeNew.setRepayInterest(0.00);
			waitingChargeNew.setRepayOverdueAmount(0.00);
			OtherFee otherFee = otherFeeMapper.selectByPrimaryKey(waitingCharge.getFeeRefId());
			waitingChargeNew.setValueDate(otherFee.getValueDate());
			waitingChargeNew.setClosingDate(otherFee.getClosingDate());
			waitingChargeNew.setAddupOverdueAmount(0.00);
			waitingChargeNew.setAddupOverdueDay(otherFee.getAddupOverdueDay());
			waitingChargeNewMapper.insert(waitingChargeNew);
		}
		
		//3、新还款计划入代扣明细表
		List<RepayPlan> repayPlanList = vo.getFeeItem().getRepayPlanList();
		for (RepayPlan repayPlan : repayPlanList) {
			WaitingChargeNew wcn = new WaitingChargeNew();
			BeanUtils.copyProperties(repayPlan, wcn);
			wcn.setId(Utils.get16UUID());
			wcn.setApplyType(LoanApplyTaskType.ExtendPeriod.getName());
			wcn.setApplyRefId(businessKey);
			wcn.setAppId(appId);
			wcn.setFeeType(FeeType.Plan.getName());
//			wcn.setFeeRefId("");//展期无关联费用id
			wcn.setPeriod(repayPlan.getPeriod());
			wcn.setDoSettle(false);
			wcn.setRepayCapital(repayPlan.getRepayCapital());
			wcn.setRepayInterest(repayPlan.getRepayInterest());
			wcn.setRepayOverdueAmount(0.00);
			wcn.setValueDate(repayPlan.getValueDate());
			wcn.setClosingDate(repayPlan.getClosingDate());
			wcn.setAddupOverdueDay(0);
			wcn.setAddupOverdueAmount(0.00);
			waitingChargeNewMapper.insert(wcn);
		}
		
		//4、启动流程
		Map<String,Object> vars = new HashMap<String,Object>();
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		vars.put("appId", appId);
		ProcessInstance processInstance = runWorkflowServiceImpl.startProcess(ETaskTag.ZHANQI.getName(), businessKey, vars);
		applyExtendPeriod.setProcInstId(processInstance.getProcessInstanceId());
		applyExtendPeriodMapper.updateByPrimaryKeySelective(applyExtendPeriod);
	}

	@Override
	public void commitApproveExtendPeriodTask(String operId,String taskId, ApproveResultVo vo) throws Exception {
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
		ApplyExtendPeriod po = applyExtendPeriodMapper.selectByPrimaryKey(businessKey);
		
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
			po.setApplyStatus(LoanApplyStatus.WaitingConfirm.getName());
		}
		else
		{
			po.setApplyStatus(LoanApplyStatus.ApproveRefuse.getName());
			//如果失败流程结束则释放总账状态
			GeneralLedger ledgerPo = generalLedgerMapper.selectByAppId(po.getAppId());
			ledgerPo.setProcessStatus("");
			generalLedgerMapper.updateByPrimaryKey(ledgerPo);
		}
		applyExtendPeriodMapper.updateByPrimaryKey(po);
		
		//5、提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("approveProcessResult", vo.getApproveResult());
		runWorkflowServiceImpl.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void commitApproveRemissionTask(String operId,String taskId, ApproveResultVo vo) throws Exception {
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
		ApplyExtendPeriod po = applyExtendPeriodMapper.selectByPrimaryKey(businessKey);
		
		//保存任务处理结果信息
		String procId = Utils.get16UUID();
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(procId);
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(vo.getApproveResult());
		taskProcessResult.setComment(vo.getApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
				
		//判断审批结果，并根据是否超过自身减免额度，如超过则由下一级来决定
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("needUpgradeApprove", false);
		vars.put("approveProcessResult", vo.getApproveResult());
		
		//通过
		if(vo.getApproveResult().equals(TaskCommitType.LOAN_PASS))
		{
			//获取减免费项
			RemissionItem remissionItemPo = remissionItemMapper.selectByApplyId(LoanApplyTaskType.ExtendPeriod.getName(), businessKey);
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
			//当任何一项减免费用超过组设定则任务需要升级处理
			if(
					(Double.compare(capital, rule.getMaxCapital())>0 ||
					Double.compare(interest, rule.getMaxInterest())>0 ||
					Double.compare(otherFee, rule.getMaxOtherFee())>0 ||
					Double.compare(lateFee, rule.getMaxLateFee())>0 ||
					Double.compare(totalOverdueAmt, rule.getMaxOverdueAmt())>0)
			  )
			{
				vars.put("needUpgradeApprove", true);
			}
			po.setApplyStatus(LoanApplyStatus.ApprovePass.getName());
		}
		else
		{
			po.setApplyStatus(LoanApplyStatus.ApproveRefuse.getName());
			//如果失败流程结束则释放总账状态
			GeneralLedger ledgerPo = generalLedgerMapper.selectByAppId(po.getAppId());
			ledgerPo.setProcessStatus("");
			generalLedgerMapper.updateByPrimaryKey(ledgerPo);
		}
		applyExtendPeriodMapper.updateByPrimaryKey(po);
		
		//提交任务
		runWorkflowServiceImpl.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void commitApplyConfirmExtendPeriodTask(String taskId, RemissionFeeItemVo vo) throws Exception 
	{
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),
				task.getTaskDefinitionKey());
		if (runpath == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		//获取申请数据
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplyExtendPeriod po = applyExtendPeriodMapper.selectByPrimaryKey(businessKey);
		

		//判断是否存在减免项（展期只能减免展期费），如有则保存展期费用并设置进入减免审批标志
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("needRemissionApprove", false);
		if(Double.compare(vo.getLateFee(), 0.00)>0)
		{
			vars.put("needRemissionApprove", true);
			RemissionItem remissionItem = new RemissionItem();
			remissionItem.setId(Utils.get16UUID());
			remissionItem.setApplyType(LoanApplyTaskType.ExtendPeriod.getName());
			remissionItem.setApplyRefId(po.getId());
			remissionItem.setCapital(0.00);
			remissionItem.setInterest(0.00);
			remissionItem.setOverdueAmount(0.00);
			remissionItem.setOtherFee(0.00);
			remissionItem.setOtherOverdueAmount(0.00);
			remissionItem.setLateFee(vo.getLateFee());
			remissionItem.setRemissionDate(vo.getRemissionDate());
			remissionItemMapper.insert(remissionItem);
		}
		
		//提交任务
		runWorkflowServiceImpl.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
	}

	@Override
	public void commitConfirmExtendPeriodTask(String operId,String taskId, ApproveResultVo vo) throws Exception 
	{
		//1、检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),task.getTaskDefinitionKey());
		if (runpath == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		ApplyExtendPeriod po = applyExtendPeriodMapper.selectByProcInstId(task.getProcessInstanceId());
		po.setApplyStatus(LoanApplyStatus.Confirmed.getName());
		
		//刷新新的变更后还款计划
		accountingServiceImpl.repayReverseAccountingUserNewWaitingChargeTable(po.getId(), LoanApplyTaskType.ExtendPeriod, po.getAppId());
		
		//释放总账状态
		GeneralLedger ledgerPo = generalLedgerMapper.selectByAppId(po.getAppId());
		ledgerPo.setProcessStatus("");
		generalLedgerMapper.updateByPrimaryKey(ledgerPo);
		
		//提交任务
		runWorkflowServiceImpl.completeTask(taskId, "", null, CommandType.COMMIT);

	}

	@Override
	public void cancelExtendPeriodTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ExtendPeriodTaskVo> getApplyExtendPeriodTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return applyExtendPeriodMapper.selectApplyExtendPeriodTaskList(createId, applyStatus);
	}

	@Override
	public ApplyExtendPeriodVo getApplyExtendPeriodTaskById(String id) 
	{
		ApplyExtendPeriodVo vo = new ApplyExtendPeriodVo();
		ApplyExtendPeriod po = applyExtendPeriodMapper.selectByPrimaryKey(id);
		vo.setApplyComment(po.getApplyComment());
		vo.setApplyEndDate(po.getApplyEndDate());
		vo.setExtendPeriod(po.getExtendPeriod());
		vo.setNewRepayMode(po.getNewRepayMode());
		
		ExtendPeriodFeeItemVo feeItemVo = new ExtendPeriodFeeItemVo();
		feeItemVo.setRepayCapital(po.getRepayCapital());
		feeItemVo.setRepayInterest(po.getRepayInterest());
		feeItemVo.setRepayOverdueAmount(po.getRepayOverdueAmount());
		feeItemVo.setOtherAmount(po.getOtherFee());
		feeItemVo.setOtherOverdueAmount(po.getOtherOverdueAmount());
		feeItemVo.setRemainCapital(po.getRemainCapital());
		feeItemVo.setNewCapital(po.getNewCapital());
		feeItemVo.setNewOldInterest(po.getNewOldInterest());
		feeItemVo.setExtendFee(po.getExtendFee());
		
		//获取产生的新的还款计划
		List<WaitingChargeNew> waitingChargeNewList = waitingChargeNewMapper.selectList(LoanApplyTaskType.ExtendPeriod.getName(), id, FeeType.Plan.getName(), false);
		List<RepayPlan>  newRepayPlanList = new ArrayList<RepayPlan>();
		for(WaitingChargeNew item : waitingChargeNewList)
		{
			RepayPlan plan = new RepayPlan();
			plan.setPeriod(item.getPeriod());
			plan.setRepayCapital(item.getRepayCapital());
			plan.setRepayInterest(item.getRepayInterest());
			plan.setRepayTotalAmount(item.getRepayCapital()+item.getRepayInterest());
			plan.setValueDate(item.getValueDate());
			plan.setClosingDate(item.getClosingDate());
			plan.setRemainCapital(item.getRemainCapitao());
			newRepayPlanList.add(plan);
		}
		feeItemVo.setRepayPlanList(newRepayPlanList);
		vo.setFeeItem(feeItemVo);
		
		//获取减免费用
		RemissionItem remissionItem = remissionItemMapper.selectByApplyId(LoanApplyTaskType.ExtendPeriod.getName(), id);
		vo.setRemissionFeeItemVo(remissionItem);
		return vo;
	}

	@Override
	public void modifyApplyExtendPeriodInfo(ApplyExtendPeriod record) {
		applyExtendPeriodMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void deleteApplyExtendPeriodInfoById(String id) {
		applyExtendPeriodMapper.deleteByPrimaryKey(id);
	}

}
