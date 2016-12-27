package com.pujjr.postloan.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.web.ServletTestExecutionListener;
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
import com.pujjr.postloan.dao.ApplySettleMapper;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.OtherFeeMapper;
import com.pujjr.postloan.dao.RemissionItemMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.WaitingChargeNewMapper;
import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.domain.RemissionItem;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LedgerProcessStatus;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.enumeration.SettleType;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.service.ISettleService;
import com.pujjr.postloan.vo.ApplySettleVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.postloan.vo.RepayingFeeItemVo;
import com.pujjr.postloan.vo.SettleFeeItemVo;
import com.pujjr.postloan.vo.SettleTaskVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class SettleServiceImpl implements ISettleService{
	@Autowired
	private IAccountingService accountingServiceImpl;
	@Autowired
	private IRunWorkflowService runWorkflowServiceImpl;
	@Autowired
	private ApplySettleMapper applySettleMapper;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	@Autowired
	private RemissionItemMapper remissionItemMapper;
	@Autowired
	private WaitingChargeNewMapper waitingChargeNewMapper;
	@Autowired
	private IPlanService planServiceImpl;
	@Autowired
	private GeneralLedgerMapper generalLedgerMapper;
	@Autowired
	private RepayPlanMapper repayPlanMapper;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private OtherFeeMapper otherFeeMapper;
	@Autowired
	private IRuleService ruleService;
	
	@Override
	public SettleFeeItemVo getAllSettleFeeItem(String appId, Date settleEffectDate) {
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();

		//还款费用分类汇总信息（逾期+当期+挂账金额）
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, true, settleEffectDate, false, true);
		BeanUtils.copyProperties(repayFeeItemVo, settleFeeItemVo);
		//当期
		RepayPlan currRepayPlan = accountingServiceImpl.getCurrentPeriodRepayPlan(appId);
		double currCapital = currRepayPlan.getRepayCapital();
		double currInterest = currRepayPlan.getRepayInterest();
		double remainCapital = settleFeeItemVo.getRemainCapital();
		//应还本金
		double repayCapital = settleFeeItemVo.getRepayCapital();
		double repayInterest = settleFeeItemVo.getRepayInterest();
		double repayOverdueAmount = settleFeeItemVo.getRepayOverdueAmount();
		double otherAmount = settleFeeItemVo.getOtherAmount();
		double otherAmountFine = settleFeeItemVo.getOtherOverdueAmount();
		//还款费用分类汇总信息（逾期）
		RepayFeeItemVo repayFeeItemVoWithoutCurr = accountingServiceImpl.getRepayingFeeItems(appId, true, settleEffectDate, false, false);
		//应还本金（逾期）
		double overdueCapital = repayFeeItemVoWithoutCurr.getRepayCapital();
		double overdueInterest = repayFeeItemVoWithoutCurr.getRepayInterest();
		double overdueFine = repayFeeItemVoWithoutCurr.getRepayOverdueAmount();
		//结清本金：（剩余本金 - 逾期本金 - 当期本金）
		double settleCapital = remainCapital - overdueCapital -currCapital ;
		double lateRate  = accountingServiceImpl.getSettleRate(appId);
		//违约金：（剩余本金 - 逾期本金）* 违约金率
		double lateFee = lateRate * (remainCapital - overdueCapital);
		//挂账金额
		double stayAmount = settleFeeItemVo.getStayAmount();
		//结清金额（逾期本金 + 逾期利息 + 逾期罚息 + 其他费用 + 其他费用罚息 + 提前还款违约金 - 挂账金额）
		double settleTotalAmt = overdueCapital + overdueInterest + overdueFine
				+ otherAmount + otherAmountFine
				+ currCapital + currInterest
				+ settleCapital
				+ lateFee
				- stayAmount;
		//结清后剩余本金
		double settleAfterAmount = 0.00;
		settleFeeItemVo.setLateFee(lateFee);
		settleFeeItemVo.setSettleCapital(settleCapital);
		settleFeeItemVo.setSettleTotalAmount(settleTotalAmt);
		settleFeeItemVo.setSettleAfterAmount(settleAfterAmount);
		settleFeeItemVo.setRepayCapital(repayCapital);
		settleFeeItemVo.setRepayInterest(repayInterest);
		settleFeeItemVo.setRepayOverdueAmount(repayOverdueAmount);
		settleFeeItemVo.setOtherAmount(otherAmount);
		settleFeeItemVo.setOtherOverdueAmount(otherAmountFine);
		settleFeeItemVo.setStayAmount(stayAmount);
		//double数据格式化
		
		//double数据格式化
		Utils.formateDoubleOfObject(settleFeeItemVo, 2);
		return settleFeeItemVo;
	}

	@Override
	public SettleFeeItemVo getPartSettleFeeItem(String appId, int beginPeriod, int endPeriod, Date settleEffectDate) {
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		//还款费用分类汇总信息（逾期+当期）
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, true, settleEffectDate, false,true);
		BeanUtils.copyProperties(repayFeeItemVo, settleFeeItemVo);
		//当期
		RepayPlan currRepayPlan = accountingServiceImpl.getCurrentPeriodRepayPlan(appId);
		//剩余本金
		double remainCapital = settleFeeItemVo.getRemainCapital();
		//应还本金
		double repayCapital = settleFeeItemVo.getRepayCapital();
		double repayInterest = settleFeeItemVo.getRepayInterest();
		double repayOverdueAmount = settleFeeItemVo.getRepayOverdueAmount();
		double otherAmount = settleFeeItemVo.getOtherAmount();
		double otherAmountFine = settleFeeItemVo.getOtherOverdueAmount();
		//还款费用分类汇总信息（逾期）
		RepayFeeItemVo repayFeeItemVoWithoutCurr = accountingServiceImpl.getRepayingFeeItems(appId, true, settleEffectDate, true,false);
		//应还本金（逾期）
		double repayCapitalWithoutCurr = repayFeeItemVoWithoutCurr.getRepayCapital();
		double overdueCapital = repayFeeItemVoWithoutCurr.getRepayCapital();
		double overdueInterest = repayFeeItemVoWithoutCurr.getRepayInterest();
		double overdueFine = repayFeeItemVoWithoutCurr.getRepayOverdueAmount();
		//当期本金
		double currCapital = currRepayPlan.getRepayCapital();
		double currInterest = currRepayPlan.getRepayInterest();
		//结清本金
		double settleCapital = 0.00;
		int queryPeriod = endPeriod - beginPeriod;
		List<RepayPlan> repayPlanList = planServiceImpl.getRepayPlanList(appId, beginPeriod, endPeriod);
		for (RepayPlan repayPlan : repayPlanList) {
			settleCapital += repayPlan.getRepayCapital();
		}
		//违约金率
		double lateRate  = accountingServiceImpl.getSettleRate(appId);
		//违约金：（剩余本金 - 逾期本金）* 违约金率
		double lateFee = lateRate * (remainCapital - overdueCapital);
		//挂账金额
		double stayAmount = settleFeeItemVo.getStayAmount();
		//结清金额
		double settleTotalAmt = overdueCapital + overdueInterest + overdueFine
				+ otherAmount + otherAmountFine
				+ currCapital + currInterest
				+ settleCapital
				+ lateFee
				- stayAmount;
		//结清后剩余本金
		double settleAfterAmount = remainCapital - repayFeeItemVo.getRepayCapital() - settleCapital;
		settleFeeItemVo.setLateFee(lateFee);
		settleFeeItemVo.setSettleCapital(settleCapital);
		settleFeeItemVo.setSettleTotalAmount(settleTotalAmt);
		settleFeeItemVo.setSettleAfterAmount(settleAfterAmount);
		settleFeeItemVo.setRepayCapital(repayCapital);
		settleFeeItemVo.setRepayInterest(repayInterest);
		settleFeeItemVo.setRepayOverdueAmount(repayOverdueAmount);
		settleFeeItemVo.setOtherAmount(otherAmount);
		settleFeeItemVo.setOtherOverdueAmount(otherAmountFine);
		settleFeeItemVo.setStayAmount(stayAmount);
		//double数据格式化
		Utils.formateDoubleOfObject(settleFeeItemVo, 2);
		return settleFeeItemVo;
	}

	@Override
	public void commitApplySettleTask(String operId,String appId,String settleType,ApplySettleVo vo) throws Exception 
	{
		accountingServiceImpl.checkCandoSettle(appId);
		//修改总账处理状态为申请提前结清,避免其他交易操作
		GeneralLedger ledgerPo = generalLedgerMapper.selectByAppId(appId);
		ledgerPo.setProcessStatus(LedgerProcessStatus.ApplySettle.getName());
		generalLedgerMapper.updateByPrimaryKey(ledgerPo);
		
		RepayPlan lastRepayPlan = accountingServiceImpl.getLatestPeriodRepayPlan(appId);
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		int endPeriod = vo.getEndPeriod();
		int beginPeriod = vo.getBeginPeriod();
		Date settleEffectDate = vo.getApplyEffectDate();
		
		ApplySettle as = new ApplySettle();
		String businessKey = Utils.get16UUID();
		as.setId(businessKey);
		as.setAppId(appId);
		if(settleType.equals(SettleType.PartSettle.getName())){
			as.setSettleType(settleType);
			settleFeeItemVo = this.getPartSettleFeeItem(appId, beginPeriod, endPeriod, settleEffectDate);
		}else{
			as.setSettleType(settleType);
			settleFeeItemVo = this.getAllSettleFeeItem(appId, settleEffectDate);
		}
		as.setRepayCapital(settleFeeItemVo.getRepayCapital());
		as.setRepayInterest(settleFeeItemVo.getRepayInterest());
		as.setRepayOverdueAmount(settleFeeItemVo.getRepayOverdueAmount());
		as.setOtherFee(settleFeeItemVo.getOtherAmount());
		as.setOtherOverdueAmount(settleFeeItemVo.getOtherOverdueAmount());
		as.setLateFee(settleFeeItemVo.getLateFee());
		as.setSettleCapital(vo.getFeeItem().getSettleCapital());
		
		//结清金额
//		double settleTotalAmt = as.getRepayCapital() + as.getRepayInterest() + as.getOtherOverdueAmount()
//				+ as.getOtherFee()+ as.getOtherOverdueAmount()
//				+ as.getSettleCapital()
//				+ as.getLateFee();
		as.setSettleTotalAmount(settleFeeItemVo.getSettleTotalAmount());
		//结清后剩余本金
//		double settleAfterCapital = settleFeeItemVo.getRemainCapital() - settleFeeItemVo.getRepayCapital() - settleFeeItemVo.getSettleTotalAmount();
		as.setSettleAfterCapital(settleFeeItemVo.getSettleAfterAmount());
		as.setSettleStartPeriod(vo.getBeginPeriod());
		as.setSettleEndPeriod(vo.getEndPeriod());
		as.setApplyComment(vo.getApplyComment());
		as.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		as.setApplyEndDate(vo.getApplyEffectDate());
		as.setCreateId(operId);
		as.setCreateDate(Utils.getDate());
		applySettleMapper.insert(as);
		
		
		/*******当期+逾期-》还款计划-》入待扣计划表（新）********/
		//2、还款计划入临时代扣明细表
		//当期还款计划
		RepayPlan currRepayPlan = accountingServiceImpl.getCurrentPeriodRepayPlan(appId);
		//待扣明细（含当期）
		List<WaitingCharge> waitingChargePlanList = accountingServiceImpl.getWaitingChargeTypePlan(appId, true);
		List<WaitingCharge> waitingChargeOtherList = accountingServiceImpl.getWaitingChargeTypeOther(appId);
		List<RepayPlan> repayPlanList = repayPlanMapper.selectRepayPlanList(appId, 1);
		List<OtherFee> otherFeeList = otherFeeMapper.selectListByAppId(appId);
		double yearRate = generalLedgerMapper.selectByAppId(appId).getYearRate();
		//转移当期+逾期至WatingChargeNew表
		for (WaitingCharge waitingCharge : waitingChargePlanList) {
			//获取关联还款计划
			RepayPlan repayPlan = repayPlanMapper.selectByPrimaryKey(waitingCharge.getFeeRefId());
			//获取当前应还总额
			double curRepayTotal = waitingCharge.getRepayCapital()+waitingCharge.getRepayInterest();
			//计算截止申请有效期之前的罚息
			int intervalDays = new Long(Utils.getTimeInterval(new Date(), vo.getApplyEffectDate(), EIntervalMode.DAYS)).intValue();
			double tmpOverdueAmount = curRepayTotal * intervalDays * ledgerPo.getDayLateRate();
			//保存信息
			WaitingChargeNew po = new WaitingChargeNew();
			po.setId(Utils.get16UUID());
			po.setApplyType(LoanApplyTaskType.Settle.getName());
			po.setApplyRefId(businessKey);
			po.setAppId(appId);
			po.setFeeType(waitingCharge.getFeeType());
			po.setFeeRefId(waitingCharge.getFeeRefId());
			po.setPeriod(repayPlan.getPeriod());
			po.setDoSettle(true);
			//如果是当期且部分提前结清把结清本金合并至当期本金中
			if(repayPlan.getPeriod()==currRepayPlan.getPeriod())
			{
				po.setRepayCapital(waitingCharge.getRepayCapital()+vo.getFeeItem().getSettleCapital());
			}
			else
			{
				po.setRepayCapital(waitingCharge.getRepayCapital());
			}
			po.setRepayInterest(waitingCharge.getRepayInterest());
			po.setRepayOverdueAmount(waitingCharge.getRepayOverdueAmount());
			po.setValueDate(repayPlan.getValueDate());
			po.setClosingDate(repayPlan.getClosingDate());
			po.setRemainCapitao(repayPlan.getRemainCapital());
			po.setAddupOverdueDay(repayPlan.getAddupOverdueDay()+intervalDays);
			po.setAddupOverdueAmount(repayPlan.getAddupOverdueAmount()+tmpOverdueAmount);
			waitingChargeNewMapper.insert(po);
		}
		//如果当前日期是当期结账日，上一步已经处理，否则需单独处理当期还款计划
		if( new Long(Utils.getTimeInterval(new Date(),currRepayPlan.getClosingDate(), EIntervalMode.DAYS)).intValue()!=0)
		{
			//保存信息
			WaitingChargeNew po = new WaitingChargeNew();
			po.setId(Utils.get16UUID());
			po.setApplyType(LoanApplyTaskType.Settle.getName());
			po.setApplyRefId(businessKey);
			po.setAppId(appId);
			po.setFeeType(FeeType.Plan.getName());
			po.setFeeRefId(currRepayPlan.getId());
			po.setPeriod(currRepayPlan.getPeriod());
			po.setDoSettle(true);
			//如果是当期且部分提前结清把结清本金合并至当期本金中
			if(settleType.equals(SettleType.PartSettle.getName()))
			{
				po.setRepayCapital(currRepayPlan.getRepayCapital()+vo.getFeeItem().getSettleCapital());
			}
			else
			{
				po.setRepayCapital(currRepayPlan.getRepayCapital());
			}
			po.setRepayInterest(currRepayPlan.getRepayInterest());
			po.setRepayOverdueAmount(0.00);
			po.setValueDate(currRepayPlan.getValueDate());
			po.setClosingDate(currRepayPlan.getClosingDate());
			po.setRemainCapitao(currRepayPlan.getRemainCapital());
			po.setAddupOverdueDay(0);
			po.setAddupOverdueAmount(0.00);
			waitingChargeNewMapper.insert(po);
		}
		//其他费用
		for (WaitingCharge waitingCharge : waitingChargeOtherList) {
			//获取其他费用申请信息
			OtherFee otherFee = otherFeeMapper.selectByPrimaryKey(waitingCharge.getFeeRefId());
			//获取当前应还总额
			double curRepayTotal = waitingCharge.getRepayCapital()+waitingCharge.getRepayInterest();
			//计算截止申请有效期之前的罚息
			int intervalDays = new Long(Utils.getTimeInterval(new Date(), vo.getApplyEffectDate(), EIntervalMode.DAYS)).intValue();
			double tmpOverdueAmount = curRepayTotal * intervalDays * ledgerPo.getDayLateRate();
			//保存信息
			WaitingChargeNew po = new WaitingChargeNew();
			po.setId(Utils.get16UUID());
			po.setApplyType(LoanApplyTaskType.Settle.getName());
			po.setApplyRefId(businessKey);
			po.setAppId(appId);
			po.setFeeType(waitingCharge.getFeeType());
			po.setFeeRefId(waitingCharge.getFeeRefId());
			po.setDoSettle(true);
			po.setRepayCapital(waitingCharge.getRepayCapital());
			po.setRepayInterest(waitingCharge.getRepayInterest());
			po.setRepayOverdueAmount(waitingCharge.getRepayOverdueAmount());
			po.setValueDate(otherFee.getValueDate());
			po.setClosingDate(otherFee.getClosingDate());
			po.setRemainCapitao(otherFee.getFeeTotalAmount());
			po.setAddupOverdueDay(otherFee.getAddupOverdueDay()+intervalDays);
			po.setAddupOverdueAmount(otherFee.getAddupOverdueAmount()+tmpOverdueAmount);
			waitingChargeNewMapper.insert(po);
		}
		
		
		/*******提前结清后剩余本金-》还款计划-》入待扣计划表（新）********/
		//结清后剩余本金生成还款计划入代扣明细表（新）
		int currPeriod = currRepayPlan.getPeriod();
		int lastPeriod = accountingServiceImpl.getLatestPeriodRepayPlan(appId).getPeriod();
		//结清后剩余本金
		double financeAmt = 0.00;
		double monthRate = 0.00;
		if(settleType.equals(SettleType.PartSettle.getName())){
			monthRate = yearRate/12;
			financeAmt = vo.getFeeItem().getSettleAfterAmount();
		}else{//全额提前结清，结清本金作为新还款计划本金基数
			financeAmt = vo.getFeeItem().getSettleCapital();
		}
		//当前还款周期下一期起息日
		Date valueDate = null;
		//当前还款周期下一期
		List<RepayPlan> nextPlanList = planServiceImpl.getRepayPlanList(appId, currPeriod+1, currPeriod+1);
		if(nextPlanList.size() == 0){
			throw new Exception("无法获取下一还款周期信息（当前为最后一期）");
		}else{
			valueDate = nextPlanList.get(0).getValueDate();
		}
		//新还款计划数据
		List<RepayPlan> willRpayPlanList = new LinkedList<RepayPlan>();
		if(settleType.equals(SettleType.PartSettle.getName())){
			willRpayPlanList = planServiceImpl.selectRefreshRepayPlanList(appId, financeAmt, monthRate, lastPeriod-currPeriod, valueDate, currPeriod);
		}else{
			willRpayPlanList = accountingServiceImpl.getAfterCurrentPeriodRepayPlan(appId, currPeriod);
		}
		for (RepayPlan repayPlan : willRpayPlanList) {
			WaitingChargeNew wcn = new WaitingChargeNew();
			wcn.setId(Utils.get16UUID());
			wcn.setApplyType(LoanApplyTaskType.Settle.getName());//提前结清
			wcn.setApplyRefId(businessKey);
			wcn.setAppId(appId);
			wcn.setFeeType(FeeType.Plan.getName());
			//获取原还款计划
			RepayPlan oldRepayPlan = repayPlanMapper.selectRepayPlan(appId, repayPlan.getPeriod());
			if(oldRepayPlan != null)
			{
				wcn.setFeeRefId(oldRepayPlan.getId());
			}
			else
			{
				wcn.setFeeRefId(Utils.get16UUID());
			}
			wcn.setPeriod(repayPlan.getPeriod());
			wcn.setRepayCapital(repayPlan.getRepayCapital());
			if(settleType.equals(SettleType.AllSettle.getName()))
			{
				wcn.setDoSettle(true);
				wcn.setRepayInterest(0.00);
			}
			else
			{
				wcn.setDoSettle(false);
				wcn.setRepayInterest(repayPlan.getRepayInterest());
			}
			wcn.setRepayOverdueAmount(0.00);
			wcn.setValueDate(repayPlan.getValueDate());
			wcn.setClosingDate(repayPlan.getClosingDate());
			wcn.setRemainCapitao(repayPlan.getRemainCapital());
			wcn.setAddupOverdueDay(0);
			wcn.setAddupOverdueAmount(0.00);
			waitingChargeNewMapper.insert(wcn);
		}
		Map<String,Object> vars = new HashMap<String,Object>();
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		vars.put("appId", appId);
		//1、启动流程
		ProcessInstance processInstance = runWorkflowServiceImpl.startProcess("TQJQ", businessKey, vars);
		as.setProcInstId(processInstance.getProcessInstanceId());
		applySettleMapper.updateByPrimaryKey(as);
	}

	@Override
	public void commitApproveSettleTask(String taskId, ApproveResultVo vo) throws Exception {
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
		ApplySettle po = applySettleMapper.selectByPrimaryKey(businessKey);
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
			GeneralLedger ledgerPo = generalLedgerMapper.selectByAppId(po.getAppId());
			ledgerPo.setProcessStatus("");
			generalLedgerMapper.updateByPrimaryKey(ledgerPo);
		}
		applySettleMapper.updateByPrimaryKey(po);
		//提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("approveProcessResult", vo.getApproveResult());
		runWorkflowServiceImpl.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void commitApproveRemissionTask(String taskId, ApproveResultVo vo) throws Exception {
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
		ApplySettle po = applySettleMapper.selectByPrimaryKey(businessKey);
		
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
			RemissionItem remissionItemPo = remissionItemMapper.selectByApplyId(LoanApplyTaskType.Settle.getName(), businessKey);
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
		applySettleMapper.updateByPrimaryKey(po);
		
		//提交任务
		runWorkflowServiceImpl.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void commitApplyConfirmSettleTask(String operId,String taskId, RemissionFeeItemVo vo) throws Exception {
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
		ApplySettle po = applySettleMapper.selectByPrimaryKey(businessKey);
		

		//判断是否存在减免项，如有则保存展期费用并设置进入减免审批标志
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("needRemissionApprove", false);
		if(
				Double.compare(vo.getLateFee(), 0.00)>0||
				Double.compare(vo.getCapital(), 0.00)>0||
				Double.compare(vo.getInterest(), 0.00)>0||
				Double.compare(vo.getOverdueAmount(), 0.00)>0||
				Double.compare(vo.getOtherFee(), 0.00)>0||
				Double.compare(vo.getOtherOverdueAmount(), 0.00)>0
		)
		{
			vars.put("needRemissionApprove", true);
			RemissionItem remissionItem = new RemissionItem();
			remissionItem.setId(Utils.get16UUID());
			remissionItem.setApplyType(LoanApplyTaskType.Settle.getName());
			remissionItem.setApplyRefId(po.getId());
			remissionItem.setCapital(vo.getCapital());
			remissionItem.setInterest(vo.getInterest());
			remissionItem.setOverdueAmount(vo.getOverdueAmount());
			remissionItem.setOtherFee(vo.getOtherFee());
			remissionItem.setOtherOverdueAmount(vo.getOtherOverdueAmount());
			remissionItem.setLateFee(vo.getLateFee());
			remissionItem.setRemissionDate(vo.getRemissionDate());
			remissionItemMapper.insert(remissionItem);
		}
		
		//提交任务
		runWorkflowServiceImpl.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
	}

	@Override
	public void commitConfirmSettleTask(String taskId, ApproveResultVo vo) throws Exception {
		//1、检查任务合法性
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
		
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplySettle po = applySettleMapper.selectByPrimaryKey(businessKey);
		po.setApplyStatus(LoanApplyStatus.Confirmed.getName());
		
		//刷新新的变更后还款计划
		accountingServiceImpl.repayReverseAccountingUserNewWaitingChargeTable(po.getId(), LoanApplyTaskType.Settle, po.getAppId());
		
		//释放总账状态
		GeneralLedger ledgerPo = generalLedgerMapper.selectByAppId(po.getAppId());
		ledgerPo.setProcessStatus("");
		generalLedgerMapper.updateByPrimaryKey(ledgerPo);
		
		//提交任务
		runWorkflowServiceImpl.completeTask(taskId, "", null, CommandType.COMMIT);
	}

	@Override
	public void cancelSettleTask(String taskId, String operId, String cancelComment) throws Exception {
		
	}

	@Override
	public List<SettleTaskVo> getApplySettleTaskList(String createId, String settleType, List<String> applyStatus) {

		return applySettleMapper.selectApplySettleTaskList(createId, settleType, applyStatus);
	}

	@Override
	public ApplySettle getApplySettleTaskById(String id) {
		return applySettleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void modifyApplySettleInfo(ApplySettle record) {
		applySettleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void deleteApplySettleInfoById(String id) {
		applySettleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<RepayPlan> getRefreshRepayPlan(String appId, double settleCapital, int period,String applyEndDate) throws ParseException {
		double settleAfterCaptital = 0.00;//结清后本金
		double monthRate = 0.00;
		Date valueDate = null;
		int currPeriod = 0;
		Date overdueEndDate = Utils.htmlTime2Date(applyEndDate, "yyyyMMdd");
		//应还费项（不含当期）
		RepayingFeeItemVo repayingFeeItem = accountingServiceImpl.getRepayingFeeItems(appId, true, overdueEndDate, false, false);
		double remainCapital = repayingFeeItem.getRemainCapital();
		double overdueCapital = repayingFeeItem.getRepayCapital();
		double overdueInterest = repayingFeeItem.getRepayInterest();
		double overdueFine = repayingFeeItem.getRepayOverdueAmount();
		double otherAmount = repayingFeeItem.getOtherAmount();
		double otherFine = repayingFeeItem.getOtherOverdueAmount();
		
		//当前还款费项
		RepayPlan currRepayPlan = accountingServiceImpl.getCurrentPeriodRepayPlan(appId);
		double currCapital = currRepayPlan.getRepayCapital();
		double currInterest = currRepayPlan.getRepayInterest();
		currPeriod = currRepayPlan.getPeriod();
		
		//最后期还款费项
		RepayPlan lastRepayPlan = accountingServiceImpl.getLatestPeriodRepayPlan(appId);
		int lastPeriod = lastRepayPlan.getPeriod();
		//当前期下一期
		RepayPlan nextRepayPlan = null;
		double nextPeriod = 0.00;
		if(currPeriod < lastPeriod){
			nextRepayPlan = accountingServiceImpl.getAfterCurrentPeriodRepayPlan(appId, 1).get(0);
			nextPeriod = nextRepayPlan.getPeriod();
			valueDate = nextRepayPlan.getValueDate();
		}else{
			//最后一期，执行全额提前结清。
		}
		//待提前结清期数所有本金
		double afterCurrCaptital = 0.00;
		if(settleCapital > 0){
			afterCurrCaptital = settleCapital;
		}else{
			List<RepayPlan> afterCurrRepayPlanList = planServiceImpl.getRepayPlanList(appId, currPeriod+1, currPeriod + period);
			for (RepayPlan repayPlan : afterCurrRepayPlanList) {
				afterCurrCaptital += repayPlan.getRepayCapital();
			}
		}
		settleAfterCaptital = remainCapital - overdueCapital - currCapital - afterCurrCaptital; 
		//总账
		GeneralLedger generalLedger = generalLedgerMapper.selectByAppId(appId);
		monthRate = generalLedger.getYearRate()/12;
		
		List<RepayPlan> newRepayPlanList = new ArrayList<RepayPlan>();
		newRepayPlanList = planServiceImpl.selectRefreshRepayPlanList(appId, settleAfterCaptital, monthRate, lastPeriod - currPeriod, valueDate, currPeriod);
		System.out.println("JSONObject.toJSONString(newRepayPlanList):"+JSONObject.toJSONString(newRepayPlanList));
		return newRepayPlanList;
	}
}
