package com.pujjr.postloan.service.impl;

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

import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.vo.SignCommitType;
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
import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.enumeration.ERemissionType;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.enumeration.SettleMode;
import com.pujjr.postloan.enumeration.SettleType;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.service.IRemissionService;
import com.pujjr.postloan.service.ISettleService;
import com.pujjr.postloan.vo.ApplySettleVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.postloan.vo.SettleFeeItemVo;
import com.pujjr.postloan.vo.SettleTaskVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
@Service
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
	
	@Override
	public SettleFeeItemVo getAllSettleFeeItem(String appId, Date settleEffectDate) {
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		//还款费用分类汇总信息（逾期+当期）
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, true, settleEffectDate, false, true);
		BeanUtils.copyProperties(repayFeeItemVo, settleFeeItemVo);
		//剩余本金
		double remainCapital = settleFeeItemVo.getRemainCapital();
		//应还本金
		double repayCapital = settleFeeItemVo.getRepayCapital();
		//还款费用分类汇总信息（逾期）
		RepayFeeItemVo repayFeeItemVoNoCurr = accountingServiceImpl.getRepayingFeeItems(appId, true, settleEffectDate, false, false);
		//应还本金（逾期）
		double repayCapitalNoCurr = repayFeeItemVoNoCurr.getRepayCapital();
		//当期本金
		double currCapital = repayCapital - repayCapitalNoCurr;
		//结清本金（不含当期）
		double settleCapital = remainCapital - repayCapital;
		double lateRate  = accountingServiceImpl.getSettleRate(appId);
		//违约金
		double lateFee = lateRate * (settleCapital + currCapital);
		//结清金额
		double settleTotalAmt = repayFeeItemVo.getRepayCapital() + repayFeeItemVo.getRepayInterest() + repayFeeItemVo.getOtherOverdueAmount()
				+ repayFeeItemVo.getOtherAmount() + repayFeeItemVo.getOtherOverdueAmount()
				+ settleCapital
				+ lateFee;
		//结清后剩余本金
		double settleAfterAmount = 0.00;
		settleFeeItemVo.setLateFee(lateFee);
		settleFeeItemVo.setSettleTotalAmount(settleTotalAmt);
		settleFeeItemVo.setSettleCapital(settleCapital);
		settleFeeItemVo.setSettleAfterAmount(settleAfterAmount);
		return settleFeeItemVo;
	}

	@Override
	public SettleFeeItemVo getPartSettleFeeItem(String appId, int beginPeriod, int endPeriod, Date settleEffectDate) {
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		//还款费用分类汇总信息（逾期+当期）
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, true, settleEffectDate, true,true);
		BeanUtils.copyProperties(repayFeeItemVo, settleFeeItemVo);
		//剩余本金
		double remainCapital = settleFeeItemVo.getRemainCapital();
		//应还本金
		double repayCapital = settleFeeItemVo.getRepayCapital();
		//还款费用分类汇总信息（逾期）
		RepayFeeItemVo repayFeeItemVoNoCurr = accountingServiceImpl.getRepayingFeeItems(appId, true, settleEffectDate, true,false);
		//应还本金（逾期）
		double repayCapitalNoCurr = repayFeeItemVoNoCurr.getRepayCapital();
		//当期本金
		double currCapital = repayCapital - repayCapitalNoCurr;
		//结清本金
		double settleCapital = 0.00;
		int queryPeriod = endPeriod - beginPeriod;
		List<RepayPlan> repayPlanList = accountingServiceImpl.getAfterCurrentPeriodRepayPlan(appId, queryPeriod);
		for (RepayPlan repayPlan : repayPlanList) {
			settleCapital += repayPlan.getRepayCapital();
		}
		//违约金率
		double lateRate  = accountingServiceImpl.getSettleRate(appId);
		//违约金
		double lateFee = lateRate * (settleCapital + currCapital);
		//结清金额
		double settleTotalAmt = repayFeeItemVo.getRepayCapital() + repayFeeItemVo.getRepayInterest() + repayFeeItemVo.getOtherOverdueAmount()
				+ repayFeeItemVo.getOtherAmount() + repayFeeItemVo.getOtherOverdueAmount()
				+ settleCapital
				+ lateFee;
		//结清后剩余本金
		double settleAfterAmount = remainCapital - repayFeeItemVo.getRepayCapital() - settleCapital;
		settleFeeItemVo.setLateFee(lateFee);
		settleFeeItemVo.setSettleCapital(settleCapital);
		settleFeeItemVo.setSettleTotalAmount(settleTotalAmt);
		settleFeeItemVo.setSettleAfterAmount(settleAfterAmount);
		return settleFeeItemVo;
	}

	@Override
	public void commitApplySettleTask(String operId,String appId,String settleType,ApplySettleVo vo) throws Exception {
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
		//计划还款
		for (WaitingCharge waitingCharge : waitingChargePlanList) {
			for (RepayPlan repayPlan : repayPlanList) {
				WaitingChargeNew wcn = new WaitingChargeNew();
				//结账日
				Date closingDate = waitingCharge.getClosingDate();
				if(waitingCharge.getFeeRefId().equals(repayPlan.getId())){
					if(waitingCharge.getRepayDate().before(currRepayPlan.getClosingDate())){//逾期
						//间隔天数：申请日----提前结清申请有效截止日 
						long createDate2EndDate = Utils.getTimeInterval(vo.getApplyDate(), vo.getApplyEffectDate(), EIntervalMode.DAYS);
						//间隔天数：结账日----提前结清申请有效截止日 （累计逾期天数）
						long closingDate2EndDate = Utils.getTimeInterval(closingDate, vo.getApplyEffectDate(), EIntervalMode.DAYS);
						//日利率
						double dayRate = yearRate/360;
						//罚息：申请日----提前结清申请有效截止日 
						double intervalFine = waitingCharge.getRepayCapital() * dayRate * createDate2EndDate;
						waitingCharge.setRepayOverdueAmount(waitingCharge.getRepayOverdueAmount() + intervalFine);
						wcn.setId(Utils.get16UUID());
						wcn.setApplyType(LoanApplyTaskType.Settle.getName());//提前结清
						wcn.setAppId(appId);
						wcn.setFeeType(waitingCharge.getFeeType());
						wcn.setPeriod(repayPlan.getPeriod());
						wcn.setDoSettle(true);
						wcn.setRepayCapital(waitingCharge.getRepayCapital());
						wcn.setRepayInterest(waitingCharge.getRepayInterest());
						wcn.setRepayOverdueAmount(waitingCharge.getRepayOverdueAmount());
						wcn.setValueDate(repayPlan.getValueDate());
						wcn.setClosingDate(repayPlan.getClosingDate());
						wcn.setAddupOverdueDay(Integer.parseInt(closingDate2EndDate+""));
						//累计罚息
						double addupOverdueAmount = waitingCharge.getRepayCapital() * dayRate * closingDate2EndDate;
						wcn.setAddupOverdueAmount(addupOverdueAmount);
					}else{//当期
						waitingCharge.setRepayOverdueAmount(waitingCharge.getRepayOverdueAmount());
						wcn.setId(Utils.get16UUID());
						wcn.setApplyType(LoanApplyTaskType.Settle.getName());//提前结清
						wcn.setAppId(appId);
						wcn.setFeeType(waitingCharge.getFeeType());
						wcn.setPeriod(repayPlan.getPeriod());
						wcn.setDoSettle(true);
						if(settleType.equals(SettleType.PartSettle.getName())){//计划还款部分提前结清：当期本金 + 结清本金
							wcn.setRepayCapital(waitingCharge.getRepayCapital() + vo.getFeeItem().getSettleCapital());
						}else{
							wcn.setRepayCapital(waitingCharge.getRepayCapital());//当期本金
						}
						wcn.setRepayInterest(waitingCharge.getRepayInterest());
						wcn.setRepayOverdueAmount(waitingCharge.getRepayOverdueAmount());
						wcn.setValueDate(repayPlan.getValueDate());
						wcn.setClosingDate(repayPlan.getClosingDate());
						wcn.setAddupOverdueDay(0);
						wcn.setAddupOverdueAmount(0.00);
					}
					waitingChargeNewMapper.insert(wcn);
				}
			}
		}
		//其他费用
		for (WaitingCharge waitingCharge : waitingChargeOtherList) {
			for (OtherFee otherFee : otherFeeList) {
				waitingCharge.setRepayOverdueAmount(waitingCharge.getRepayOverdueAmount());
				WaitingChargeNew wcn = new WaitingChargeNew();
				wcn.setId(Utils.get16UUID());
				wcn.setApplyType(LoanApplyTaskType.Settle.getName());//提前结清
				wcn.setAppId(appId);
				wcn.setFeeType(waitingCharge.getFeeType());
//				wcn.setPeriod(repayPlan.getPeriod());
				wcn.setDoSettle(true);
				wcn.setRepayCapital(waitingCharge.getRepayCapital());//当期本金
				wcn.setRepayInterest(waitingCharge.getRepayInterest());
				wcn.setRepayOverdueAmount(waitingCharge.getRepayOverdueAmount());
				wcn.setValueDate(otherFee.getValueDate());
				wcn.setClosingDate(otherFee.getClosingDate());
				wcn.setAddupOverdueDay(otherFee.getAddupOverdueDay());
				wcn.setAddupOverdueAmount(otherFee.getAddupOverdueAmount());
			}
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
			willRpayPlanList = planServiceImpl.selectRefreshRepayPlanList(appId, financeAmt, currPeriod, lastPeriod);
		}
		for (RepayPlan repayPlan : willRpayPlanList) {
			WaitingChargeNew wcn = new WaitingChargeNew();
			wcn.setId(Utils.get16UUID());
			wcn.setApplyType(LoanApplyTaskType.Settle.getName());//提前结清
			wcn.setAppId(appId);
			wcn.setFeeType(FeeType.Plan.getName());
			wcn.setPeriod(repayPlan.getPeriod());
			wcn.setDoSettle(true);
			wcn.setRepayCapital(repayPlan.getRepayCapital());
			wcn.setRepayInterest(repayPlan.getRepayInterest());
			wcn.setRepayOverdueAmount(0.00);
			wcn.setValueDate(repayPlan.getValueDate());
			wcn.setClosingDate(repayPlan.getClosingDate());
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
		//1、检查任务合法性
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
		taskProcessResult.setProcessResult(vo.getApproveResult());
		// 建议通过
		if (vo.getApproveResult().equals(TaskCommitType.PASS)) 
		{
			taskProcessResult.setProcessResultDesc("通过");
		} 
		else
		{
			taskProcessResult.setProcessResultDesc("拒绝");
		}
		taskProcessResult.setComment(vo.getApproveComment());
		taskProcessResult.setTaskBusinessId(Utils.get16UUID());
		taskProcessResultDao.insert(taskProcessResult);	
		//3、处理结果放入流程变量,完成任务
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("approveProcessResult", vo.getApproveResult());
		runWorkflowServiceImpl.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
	}

	@Override
	public void commitApplyConfirmSettleTask(String operId,String taskId, RemissionFeeItemVo vo) throws Exception {
		//1、检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),
				task.getTaskDefinitionKey());
		if (runpath == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		ApplySettle applySettle = applySettleMapper.selectByProcInstId(task.getProcessInstanceId());
		//2、获取"提前结清减免"对应"提前结清申请"信息
		RemissionItem remissiontItem = new RemissionItem();
		remissiontItem.setId(Utils.get16UUID());
		remissiontItem.setApplyType(ERemissionType.SETTLE.getName());
		remissiontItem.setApplyRefId(applySettle.getId());//关联提前结清申请主键
		remissiontItem.setCapital(vo.getCapital());
		remissiontItem.setInterest(vo.getInterest());
		remissiontItem.setOverdueAmount(vo.getOverdueAmount());
		remissiontItem.setOtherFee(vo.getOtherFee());
		remissiontItem.setOtherOverdueAmount(vo.getOtherOverdueAmount());
		remissiontItem.setLateFee(vo.getLateFee());
		remissionItemMapper.insert(remissiontItem);
		// 3、处理结果放入流程变量,完成任务
		HashMap<String, Object> vars = new HashMap<String, Object>();
		//vars变量待定....
		runWorkflowServiceImpl.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
	}

	@Override
	public void commitConfirmSettleTask(String taskId, ApproveResultVo vo) throws Exception {
		//1、检查任务合法性
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
		taskProcessResult.setProcessResult(vo.getApproveResult());
		// 建议通过
		if (vo.getApproveResult().equals(TaskCommitType.PASS)) 
		{
			taskProcessResult.setProcessResultDesc("通过");
			ApplySettle applySettle = applySettleMapper.selectByProcInstId(task.getProcessInstanceId());
			accountingServiceImpl.repayReverseAccountingUserNewWaitingChargeTable(applySettle.getId(), LoanApplyTaskType.Settle, applySettle.getAppId());
			/*//刷新还款计划
			ApplySettle applySettle = applySettleMapper.selectByProcInstId(task.getProcessInstanceId());
			String id = applySettle.getId();
			String appId = applySettle.getAppId();
			GeneralLedger generalLedger = generalLedgerMapper.selectByAppId(appId);
			//部分提前结清后剩余本金
			double financeAmt = applySettle.getSettleAfterCapital();
			double monthRate = generalLedger.getYearRate()/12;
			RepayPlan lastRepayPlan = accountingServiceImpl.getLatestPeriodRepayPlan(appId);
			RepayPlan currRepayPlan = accountingServiceImpl.getCurrentPeriodRepayPlan(appId);
			int currPeriod = currRepayPlan.getPeriod();
			//剩余还款期数
			int period = lastRepayPlan.getPeriod() - currPeriod;
			//当前还款周期下一期
			List<RepayPlan> nextPlanList = planServiceImpl.getRepayPlanList(appId, currPeriod+1, currPeriod+1);
			if(nextPlanList.size() == 0){
				throw new Exception("无法获取下一还款周期信息（当前为最后一期）");
			}else{
				Date valueDate = nextPlanList.get(0).getValueDate();
				EInterestMode eInterestMode = planServiceImpl.getInterestMode(appId);
				planServiceImpl.refreshRepayPlan(id, financeAmt, monthRate, period, valueDate, eInterestMode, currPeriod);
			}*/
		} 
		else{
			taskProcessResult.setProcessResultDesc("拒绝");
		}
		taskProcessResult.setComment(vo.getApproveComment());
		taskProcessResult.setTaskBusinessId(Utils.get16UUID());
		taskProcessResultDao.insert(taskProcessResult);	
		//3、修改申请状态
		ApplySettle applySettle = applySettleMapper.selectByProcInstId(task.getProcessInstanceId());
		applySettle.setApplyStatus(vo.getApproveResult());
		applySettleMapper.updateByPrimaryKeySelective(applySettle);
		//4、处理结果放入流程变量,完成任务
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("approveProcessResult", vo.getApproveResult());
		runWorkflowServiceImpl.completeTask(taskId, "提交任务", vars, CommandType.COMMIT);
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
}
