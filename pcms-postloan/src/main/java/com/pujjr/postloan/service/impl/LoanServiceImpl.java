package com.pujjr.postloan.service.impl;

import java.util.Date;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.carcredit.constant.ApplyStatus;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.service.ILoanService;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.utils.Utils;

@Service
public class LoanServiceImpl implements ILoanService {
	
	@Autowired
	private IApplyService applyService;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private IPlanService planService;
	@Autowired
	private IRunWorkflowService  runWorkflowService;
	
	@Override
	public void commitLoanTask(String taskId, String appId) throws Exception {
		// TODO Auto-generated method stub
		//查询任务信息
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null)
		{
			throw new Exception("提交任务失败,任务ID"+taskId+"对应任务不存在 ");
		}
		
		//修改申请单状态为放款完成
		Apply apply = applyService.getApply(appId);
		apply.setStatus(ApplyStatus.LOAN_COMPLETE);
		applyService.updateApply(apply);
				
		//查询申请信息
		ApplyVo applyVo = applyService.getApplyDetail(appId);
		
		//创建总账信息
		GeneralLedger ledgerPo =  new GeneralLedger();
		ledgerPo.setId(Utils.get16UUID());
		ledgerPo.setAppId(appId);
		ledgerPo.setProductName(applyVo.getProduct().getProductName());
		ledgerPo.setProductCode(applyVo.getProductCode());
		ledgerPo.setPeriod(applyVo.getPeriod());
		ledgerPo.setFinanceAmt(applyVo.getTotalFinanceAmt());
		ledgerPo.setYearRate(applyVo.getProduct().getYearRate());
		ledgerPo.setDayLateRate(applyVo.getProduct().getDayLateRate());
		ledgerPo.setDayExtendRate(applyVo.getProduct().getDayExtendRate());
		String repayMode = applyVo.getProduct().getRepayMode();
		ledgerPo.setRepayMode(repayMode);
		ledgerPo.setLoanDate(new Date());
		ledgerPo.setRemainCapital(applyVo.getTotalFinanceAmt());
		ledgerPo.setRepayStatus(RepayStatus.Repaying.getName());
		ledgerPo.setIsOffering(false);
		ledgerPo.setAddupOverdueTime(0);
		ledgerPo.setAddupOverdueDay(0);
		ledgerDao.insert(ledgerPo);
		
		//创建还款计划-等额本息
		if(repayMode.equals(EInterestMode.CPM.getDictCode()))
		{
			planService.generateRepayPlan(appId, applyVo.getTotalFinanceAmt(), applyVo.getProduct().getYearRate()/12, applyVo.getPeriod(), new Date(), EInterestMode.CPM);
		}
		
		//创建还款计划-等额利息
		if(repayMode.equals(EInterestMode.CONST.getDictCode()))
		{
			planService.generateRepayPlan(appId, applyVo.getTotalFinanceAmt(), applyVo.getProduct().getYearRate()/12, applyVo.getPeriod(), new Date(), EInterestMode.CONST);
		}
		
		//创建还款计划-一次付息按月还款
		if(repayMode.equals(EInterestMode.MONTLY.getDictCode()))
		{
			planService.generateRepayPlan(appId, applyVo.getTotalFinanceAmt(), applyVo.getProduct().getYearRate()/12, applyVo.getPeriod(), new Date(), EInterestMode.MONTLY);
		}
		
		//创建还款计划-按月付息到期还本
		if(repayMode.equals(EInterestMode.ONETIME.getDictCode()))
		{
			planService.generateRepayPlan(appId, applyVo.getTotalFinanceAmt(), applyVo.getProduct().getYearRate()/12, applyVo.getPeriod(), new Date(), EInterestMode.ONETIME);
		}	
		
		//提交任务
		runWorkflowService.completeTask(taskId, "提交任务", null, CommandType.COMMIT);
	}

}
