package com.pujjr.carcredit.exthandle;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.jbpm.core.handle.ITaskCompleteHandle;
@Service("ReloadBusinessData")
public class ReloadBusinessData implements ITaskCompleteHandle {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ISysBranchService sysBranchService;
	@Override
	public void handle(TaskEntity taskEntity) 
	{
		// TODO Auto-generated method stub
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplyVo apply = applyService.getUnCommitApplyDetail(businessKey);
		double totalFinanceAmt = 5000;
		taskEntity.getExecution().setVariable("totalFinanceAmount", totalFinanceAmt);
		
	}

}
