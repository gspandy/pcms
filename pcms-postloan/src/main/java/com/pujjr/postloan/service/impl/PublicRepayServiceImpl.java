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
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.dao.ApplyPublicRepayMapper;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.domain.ApplyPublicRepay;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.enumeration.LedgerProcessStatus;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IPublicRepayService;
import com.pujjr.postloan.vo.ApplyPublicRepayVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.PublicRepayTaskVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
public class PublicRepayServiceImpl implements IPublicRepayService {

	@Autowired
	private IAccountingService accountingService;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private ApplyPublicRepayMapper publicRepayDao;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	@Override
	public RepayFeeItemVo getPublicRepayFeeItem(String appId) {
		// TODO Auto-generated method stub
		return accountingService.getRepayingFeeItems(appId, false, null, true,true);
	}

	@Override
	public void commitApplyPublicRepayTask(String appId, ApplyPublicRepayVo vo) throws Exception {
		// TODO Auto-generated method stub
		accountingService.checkCandoPublicRepay(appId);
		/**
		 * 修改总账处理状态为申请对公还款,避免其他交易操作
		 * **/
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		ledgerPo.setProcessStatus(LedgerProcessStatus.ApplyPublicRepay.getName());
		ledgerDao.updateByPrimaryKey(ledgerPo);
		/**
		 * 创建申请信息
		 */
		ApplyPublicRepay po = new ApplyPublicRepay();
		String businessKey = Utils.get16UUID();
		po.setId(businessKey);
		po.setAppId(appId);
		po.setRepayCapital(vo.getFeeItem().getRepayCapital());
		po.setRepayInterest(vo.getFeeItem().getRepayInterest());
		po.setRepayOverdueAmount(vo.getFeeItem().getRepayOverdueAmount());
		po.setOtherFee(vo.getFeeItem().getOtherAmount());
		po.setOtherOverdueAmount(vo.getFeeItem().getOtherOverdueAmount());
		po.setChargeAmount(vo.getChargeAmount());
		po.setChargeDate(vo.getChargeDate());
		po.setApplyComment(vo.getApplyComment());
		po.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		po.setCreateId(vo.getCreateId());
		po.setCreateDate(new Date());
		/**
		 * 启动流程
		 */
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, vo.getCreateId());
		ProcessInstance instance = runWorkflowService.startProcess("DGHK", businessKey, vars);
		po.setProcInstId(instance.getProcessInstanceId());
		publicRepayDao.insert(po);
	}

	@Override
	public void commitApprovePublicRepayTask(String taskId, ApproveResultVo vo) throws Exception {
		// TODO Auto-generated method stub
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null)
		{
			throw new Exception("查询任务失败,任务ID"+taskId+"对应任务不存在 ");
		}
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplyPublicRepay po = publicRepayDao.selectByPrimaryKey(businessKey);
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(), task.getTaskDefinitionKey());
		
		//保存任务处理结果信息
		String procId = Utils.get16UUID();
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(procId);
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(vo.getApproveResult());
		taskProcessResult.setComment(vo.getApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
		//通过做还款操作
		if(vo.getApproveResult().equals(TaskCommitType.LOAN_PASS))
		{
			po.setApplyStatus(LoanApplyStatus.ApprovePass.getName());
			accountingService.repayReverseAccounting(po.getAppId(), po.getChargeAmount(), po.getChargeDate(), RepayMode.PublicRepay,null);
		}
		else
		{
			po.setApplyStatus(LoanApplyStatus.ApproveRefuse.getName());
		}
		publicRepayDao.updateByPrimaryKey(po);
		//提交任务
		runWorkflowService.completeTask(taskId, "", null, CommandType.COMMIT);
		//释放总账状态
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(po.getAppId());
		ledgerPo.setProcessStatus("");
		ledgerDao.updateByPrimaryKey(ledgerPo);
		
	}

	@Override
	public void cancelPublicRepayTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PublicRepayTaskVo> getApplyPublicRepayTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return publicRepayDao.selectApplyPublicRepayTaskList(createId, applyStatus);
	}

	@Override
	public ApplyPublicRepay getApplyPublicRepayTaskById(String id) {
		// TODO Auto-generated method stub
		return publicRepayDao.selectByPrimaryKey(id);
	}

	@Override
	public void modifyApplyPublicRepayInfo(ApplyPublicRepay record) {
		// TODO Auto-generated method stub
		publicRepayDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteApplyPublicRepayInfoById(String id) {
		// TODO Auto-generated method stub
		publicRepayDao.deleteByPrimaryKey(id);
	}

}
