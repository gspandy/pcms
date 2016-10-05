package com.pujjr.carcredit.exthandle;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.RuleFinanceAmount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.service.IProductService;
import com.pujjr.base.service.IRuleService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.jbpm.core.handle.ITaskCompleteHandle;
@Service("AfterApproveHandle")
public class AfterApproveHandle implements ITaskCompleteHandle {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private ISysWorkgroupService workgroupService;
	@Autowired
	private IRuleService ruleService;
	@Autowired
	private IProductService productService;
	@Override
	public void handle(TaskEntity taskEntity) 
	{
		// TODO Auto-generated method stub
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplyVo apply = applyService.getApplyDetail(businessKey);
		double totalFinanceAmt = apply.getTotalFinanceAmt();
		//获取审批任务执行者所属工作组
		String batchAssigneeWorkgroupId = taskEntity.getExecution().getVariable("batchAssigneeWorkgroupId").toString();
		RuleFinanceAmount ruleFinanceAmount = ruleService.getWorkgroupFinanceAmountRule(batchAssigneeWorkgroupId);
		if(ruleFinanceAmount==null)
		{
			taskEntity.getExecution().setVariable("approveMinAmount", 0);
			taskEntity.getExecution().setVariable("approveMaxAmount", 100000000);
		}
		else
		{
			taskEntity.getExecution().setVariable("approveMinAmount", ruleFinanceAmount.getMinAmount());
			taskEntity.getExecution().setVariable("approveMaxAmount", ruleFinanceAmount.getMaxAmount());
		}
		Product product = productService.getProductByProductCode(apply.getProductCode());
		taskEntity.getExecution().setVariable("isTotalReFinance", product.getProductRule().getIsTotalRefinance());
		taskEntity.getExecution().setVariable("totalFinanceAmount", totalFinanceAmt);
		
	}

}
