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

import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.dao.ApplyRefundMapper;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.StayAccountMapper;
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.ApplyRefund;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.StayAccount;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LedgerProcessStatus;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IRefundService;
import com.pujjr.postloan.vo.ApplyRefundVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.NewRepayPlanVo;
import com.pujjr.postloan.vo.RefundTaskVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.utils.Utils;
@Service
public class RefundServiceImpl implements IRefundService {

	@Autowired
	private IAccountingService accountingService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private ApplyRefundMapper applyRefundDao;
	@Autowired
	private StayAccountMapper stayAccountDao;
	
	@Override
	public RepayFeeItemVo getRefundFeeItem(String appId) {
		// TODO Auto-generated method stub
		return accountingService.getRepayingFeeItems(appId, false, null, false, false);
	}

	@Override
	public void commitApplyRefundTask(String appId, ApplyRefundVo vo ,String operId) throws Exception {
		// TODO Auto-generated method stub
		//申请信息的验证
		double stayAmount = accountingService.getStayAmount(appId);
		if(Double.compare(stayAmount, vo.getRefundAmount())<0)
		{
			throw new Exception("退款金额不得大于挂账金额");
		}
		
		//修改总账处理状态为退款申请,避免其他交易操作
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		ledgerPo.setProcessStatus(LedgerProcessStatus.ApplyRefund.getName());
		ledgerDao.updateByPrimaryKey(ledgerPo);
		
		//创建申请信息
		ApplyRefund po = new ApplyRefund();
		String businessKey = Utils.get16UUID();
		po.setId(businessKey);
		po.setAppId(appId);
		po.setStayAmount(vo.getStayAmount());
		po.setStayDay(0);
		po.setRefundAmount(vo.getRefundAmount());
		po.setRefundDate(vo.getRefundDate());
		po.setApplyComment(vo.getApplyComment());
		po.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		po.setCreateId(operId);
		po.setCreateDate(new Date());
		
		//启动流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("TUIKUAN", businessKey, vars);
		po.setProcInstId(instance.getProcessInstanceId());
		applyRefundDao.insert(po);
	}

	@Override
	public void commitApproveRefundTask(String taskId, ApproveResultVo vo) throws Exception {
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
		ApplyRefund po = applyRefundDao.selectByPrimaryKey(businessKey);
		
		//保存任务处理结果信息
		String procId = Utils.get16UUID();
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(procId);
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(vo.getApproveResult());
		taskProcessResult.setComment(vo.getApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
		
		//根据审批结果执行相关操作
		if(vo.getApproveResult().equals(TaskCommitType.LOAN_PASS))
		{
			po.setApplyStatus(LoanApplyStatus.WaitingConfirm.getName());
		}
		else
		{
			po.setApplyStatus(LoanApplyStatus.ApproveRefuse.getName());
			//如果失败流程结束则释放总账状态
			GeneralLedger ledgerPo = ledgerDao.selectByAppId(po.getAppId());
			ledgerPo.setProcessStatus("");
			ledgerDao.updateByPrimaryKey(ledgerPo);
		}
		applyRefundDao.updateByPrimaryKey(po);
		
		//提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("approveProcessResult", vo.getApproveResult());
		runWorkflowService.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void cancelRefundTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RefundTaskVo> getApplyRefundTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return applyRefundDao.selectApplyRefundTaskList(createId, applyStatus);
	}

	@Override
	public ApplyRefund getApplyRefundTaskById(String id) {
		// TODO Auto-generated method stub
		return applyRefundDao.selectByPrimaryKey(id);
	}

	@Override
	public void modifyApplyRefundInfo(ApplyRefund record) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteApplyRefundInfoById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitConfirmRefundTask(String taskId) throws Exception {
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
		ApplyRefund po = applyRefundDao.selectByPrimaryKey(businessKey);
		po.setApplyStatus(LoanApplyStatus.Confirmed.getName());
		applyRefundDao.updateByPrimaryKey(po);
		
		//退款操作
		StayAccount stayAccount = stayAccountDao.selectByAppId(po.getAppId());
		double stayAmount = Double.compare(stayAccount.getStayAmount(),po.getRefundAmount())>0?stayAccount.getStayAmount()-po.getRefundAmount():0.00;
		stayAccount.setStayAmount(stayAmount);
		stayAccountDao.updateByPrimaryKey(stayAccount);
		
		//释放总账状态
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(po.getAppId());
		ledgerPo.setProcessStatus("");
		ledgerDao.updateByPrimaryKey(ledgerPo);
		
		//提交任务
		runWorkflowService.completeTask(taskId, "", null, CommandType.COMMIT);		
	}

}
