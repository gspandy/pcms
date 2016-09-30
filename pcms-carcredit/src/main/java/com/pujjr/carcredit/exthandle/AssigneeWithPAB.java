package com.pujjr.carcredit.exthandle;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.jbpm.core.handle.ITaskAssigneeHandle;
/**
 * 按照产品编码、融资金额、经销商进行任务分单
 * **/
@Service("AssigneeWithPAB")
public class AssigneeWithPAB implements ITaskAssigneeHandle {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ITaskService taskService;
	@Autowired
	private ISysBranchService sysBranchService;

	@Override
	public String handle(String assigneeParam,TaskEntity taskEntity) {
		// TODO Auto-generated method stub
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplyVo apply = applyService.getUnCommitApplyDetail(businessKey);
		SysBranch sysBranch = sysBranchService.getSysBranch(null, apply.getCreateBranchCode());
		String assignee = taskService.getProcessTaskAccount(apply.getProductCode(), apply.getTotalFinanceAmt(), sysBranch.getId(), assigneeParam, null);
		return assignee;
	}

}
