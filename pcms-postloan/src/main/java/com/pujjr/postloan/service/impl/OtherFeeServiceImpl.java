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
import com.pujjr.postloan.dao.OtherFeeDetailMapper;
import com.pujjr.postloan.dao.OtherFeeMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.domain.OtherFeeDetail;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.BatchProcessStatus;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LedgerProcessStatus;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.enumeration.OfferStatus;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.service.IOtherFeeService;
import com.pujjr.postloan.vo.ApplyOtherFeeVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.NewRepayPlanVo;
import com.pujjr.postloan.vo.OtherFeeDetailVo;
import com.pujjr.postloan.vo.OtherFeeTaskVo;
import com.pujjr.utils.Utils;
@Service
public class OtherFeeServiceImpl implements IOtherFeeService 
{
	@Autowired
	private OtherFeeMapper otherFeeDao;
	@Autowired
	private OtherFeeDetailMapper otherFeeDetailDao;
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
	private WaitingChargeMapper waitingchargeDao;
	
	@Override
	public void commitApplyOtherFeeTask(String appId,ApplyOtherFeeVo vo, String operId) 
	{
		// TODO Auto-generated method stub
		//创建申请信息
		OtherFee po = new OtherFee();
		String businessKey = Utils.get16UUID();
		po.setId(businessKey);
		po.setAppId(appId);
		po.setFeeTotalAmount(vo.getFeeTotalAmount());
		po.setValueDate(new Date());
		po.setClosingDate(new Date());
		po.setAddupOverdueDay(0);
		po.setAddupOverdueAmount(0.00);
		po.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		po.setApplyComment(vo.getApplyComment());
		po.setCreateId(operId);
		po.setCreateDate(new Date());
		otherFeeDao.insert(po);
		
		//保存费用明细
		for(OtherFeeDetailVo item : vo.getDetailList())
		{
			OtherFeeDetail detailPo = new OtherFeeDetail();
			detailPo.setId(Utils.get16UUID());
			detailPo.setItemName(item.getItemName());
			detailPo.setItemAmount(item.getItemAmount());
			detailPo.setOtherfeeApplyId(businessKey);
			otherFeeDetailDao.insert(detailPo);
		}
		
		//启动流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("OTHERFEE", businessKey, vars);
		po = otherFeeDao.selectByPrimaryKey(businessKey);
		po.setProcInstId(instance.getId());
		otherFeeDao.updateByPrimaryKey(po);
	}

	@Override
	public void commitApproveOtherFeeTask(String taskId, ApproveResultVo vo) throws Exception 
	{
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
		OtherFee po = otherFeeDao.selectByPrimaryKey(businessKey);
		
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
			//审批通过日期作为最终扣款日期，加入待扣款明细表
			Date chargeDate = new Date();
			WaitingCharge waitingChargePo = new WaitingCharge();
			waitingChargePo.setId(Utils.get16UUID());
			waitingChargePo.setAppId(po.getAppId());
			waitingChargePo.setFeeType(FeeType.Other.getName());
			waitingChargePo.setFeeRefId(po.getId());
			waitingChargePo.setRepayCapital(po.getFeeTotalAmount());
			waitingChargePo.setRepayInterest(0.00);
			waitingChargePo.setRepayOverdueAmount(0.00);
			waitingChargePo.setClosingDate(chargeDate);
			waitingChargePo.setRepayDate(chargeDate);
			waitingChargePo.setOfferStatus(OfferStatus.WaitOffer.getName());
			waitingChargePo.setGenTime(chargeDate);
			waitingChargePo.setVersionId(1);
			waitingChargePo.setBatchProcessStatus(BatchProcessStatus.Waiting.getName());
			waitingchargeDao.insert(waitingChargePo);
			
			//更新下其他费用的申请信息
			po.setApplyStatus(LoanApplyStatus.ApprovePass.getName());
			po.setValueDate(chargeDate);
			po.setClosingDate(chargeDate);
			po.setRepayStatus(RepayStatus.Repaying.getName());
		}
		else
		{
			po.setApplyStatus(LoanApplyStatus.ApproveRefuse.getName());
		}
		otherFeeDao.updateByPrimaryKey(po);
		
		//5、提交任务
		runWorkflowService.completeTask(taskId, "", null, CommandType.COMMIT);
	}

	@Override
	public ApplyOtherFeeVo getApplyOtherFeeTaskById(String id) 
	{
		// TODO Auto-generated method stub
		ApplyOtherFeeVo vo = new ApplyOtherFeeVo();
		OtherFee po = otherFeeDao.selectByPrimaryKey(id);
		vo.setApplyComment(po.getApplyComment());
		vo.setFeeTotalAmount(po.getFeeTotalAmount());
		List<OtherFeeDetailVo> detailVo = otherFeeDao.selectFeeDetailList(po.getId());
		vo.setDetailList(detailVo);
		return vo;
	}

	@Override
	public List<OtherFeeTaskVo> getApplyOtherFeeTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return otherFeeDao.selectApplyOtherFeeTaskList(createId, applyStatus);
	}

}
