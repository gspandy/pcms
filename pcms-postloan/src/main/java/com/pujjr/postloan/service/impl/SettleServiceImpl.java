package com.pujjr.postloan.service.impl;

import java.util.Date;
import java.util.HashMap;
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
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.dao.ApplySettleMapper;
import com.pujjr.postloan.dao.RemissionItemMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.WaitingChargeNewMapper;
import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.domain.RemissionItem;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.ERemissionType;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.SettleMode;
import com.pujjr.postloan.service.IAccountingService;
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
	
	@Override
	public SettleFeeItemVo getAllSettleFeeItem(String appId, Date settleEffectDate) {
		boolean isCalOverdueAmount = true;
		boolean isReduceStayAmount = true;
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, isCalOverdueAmount, settleEffectDate, isReduceStayAmount,true);
		BeanUtils.copyProperties(repayFeeItemVo, settleFeeItemVo);
		//剩余本金
		double remainCapital = settleFeeItemVo.getRemainCapital();
		//应还本金
		double repayCapital = settleFeeItemVo.getRepayCapital();
		//结清本金
		double settleCapital = remainCapital - repayCapital;
		//当期已起息未结账本金
		double currRepayCapital = accountingServiceImpl.getCurrentPeriodRepayPlan(appId).getRepayCapital();
		double lateRate  = accountingServiceImpl.getSettleRate(appId);
		//违约金
		double lateFee = lateRate * (settleCapital + currRepayCapital);
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
		boolean isCalOverdueAmount = true;
		boolean isReduceStayAmount = true;
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, isCalOverdueAmount, settleEffectDate, isReduceStayAmount,true);
		BeanUtils.copyProperties(repayFeeItemVo, settleFeeItemVo);
		//剩余本金
		double remainCapital = settleFeeItemVo.getRemainCapital();
		//应还本金
		double repayCapital = settleFeeItemVo.getRepayCapital();
		//结清本金
		double settleCapital = 0.00;
		int queryPeriod = endPeriod - beginPeriod;
		List<RepayPlan> repayPlanList = accountingServiceImpl.getAfterCurrentPeriodRepayPlan(appId, queryPeriod);
		for (RepayPlan repayPlan : repayPlanList) {
			settleCapital += repayPlan.getRepayCapital();
		}
		//当期已起息未结账本金
		double currRepayCapital = accountingServiceImpl.getCurrentPeriodRepayPlan(appId).getRepayCapital();
		double lateRate  = accountingServiceImpl.getSettleRate(appId);
		//违约金
		double lateFee = lateRate * (settleCapital + currRepayCapital);
		//结清金额
		double settleTotalAmt = repayFeeItemVo.getRepayCapital() + repayFeeItemVo.getRepayInterest() + repayFeeItemVo.getOtherOverdueAmount()
				+ repayFeeItemVo.getOtherAmount() + repayFeeItemVo.getOtherOverdueAmount()
				+ settleCapital
				+ lateFee;
		//结清后剩余本金
		double settleAfterAmount = remainCapital - repayCapital - settleCapital;
		settleFeeItemVo.setLateFee(lateFee);
		settleFeeItemVo.setSettleCapital(settleCapital);
		settleFeeItemVo.setSettleAfterAmount(0.00);
		settleFeeItemVo.setSettleTotalAmount(settleTotalAmt);
		settleFeeItemVo.setSettleAfterAmount(settleAfterAmount);
		return settleFeeItemVo;
	}

	@Override
	public void commitApplySettleTask(String operId,String appId, ApplySettleVo vo) {
		RepayPlan lastRepayPlan = accountingServiceImpl.getLatestPeriodRepayPlan(appId);
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		int endPeriod = vo.getEndPeriod();
		int beginPeriod = vo.getBeginPeriod();
		Date settleEffectDate = vo.getApplyEffectDate();
		
		ApplySettle as = new ApplySettle();
		String businessKey = Utils.get16UUID();
		as.setId(businessKey);
		as.setAppId(appId);
		if(vo.getEndPeriod() < lastRepayPlan.getPeriod()){
			as.setSettleType("部分提前结清");
			settleFeeItemVo = this.getPartSettleFeeItem(appId, beginPeriod, endPeriod, settleEffectDate);
		}else{
			as.setSettleType("全额提前结清");
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
		as.setApplyStatus("申请已提交");
		as.setApplyEndDate(vo.getApplyEffectDate());
		as.setCreateId(operId);
		as.setCreateDate(Utils.getDate());
		applySettleMapper.insert(as);
		
		Map<String,Object> vars = new HashMap<String,Object>();
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		vars.put("appId", appId);
		//启动流程
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
			//初始化临时代扣明细表
			ApplySettle as = applySettleMapper.selectByProcInstId(task.getProcessInstanceId());
			WaitingChargeNew wcn = new WaitingChargeNew();
			wcn.setId(Utils.get16UUID());
			wcn.setApplyType(ERemissionType.REMISSION.getName());
			wcn.setApplyRefId(as.getId());
			wcn.setAppId(as.getAppId());
			wcn.setFeeType(FeeType.Other.getName());
			wcn.setFeeRefId(as.getId());
			wcn.setDoSettle(true);
			wcn.setRepayCapital(as.getRepayCapital());
			wcn.setRepayInterest(as.getRepayInterest());
			wcn.setRepayOverdueAmount(as.getOtherOverdueAmount());
			waitingChargeNewMapper.insert(wcn);
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
		runWorkflowServiceImpl.completeTask(taskId, "", vars, CommandType.COMMIT);
		//4、修改提前结清申请表状态
		ApplySettle applySettle = applySettleMapper.selectByProcInstId(task.getProcessInstanceId());
		applySettle.setApplyStatus(vo.getApproveResult());
		applySettleMapper.updateByPrimaryKeySelective(applySettle);
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
		} 
		else
		{
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
		
		//冲账

	}

	@Override
	public void cancelSettleTask(String taskId, String operId, String cancelComment) throws Exception {
		
	}

	@Override
	public List<SettleTaskVo> getApplySettleTaskList(String createId, String settleType, List<String> applyStatus) {

		return null;
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
