package com.pujjr.carcredit.exthandle;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
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
	@Autowired
	private ISysParamService sysParamService;

	@Override
	public String handle(String assigneeParam,TaskEntity taskEntity) {
		// TODO Auto-generated method stub
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult().getBusinessKey();
		ApplyVo apply = applyService.getApplyDetail(businessKey);
		SysBranch sysBranch = sysBranchService.getSysBranch(null, apply.getCreateBranchCode());
		ProcessTaskUserBo assignee = taskService.getProcessTaskAccount(apply.getProductCode(), apply.getTotalFinanceAmt(), sysBranch.getId(), assigneeParam, null);
		//如果未找到任务执行者，则分配给系统默认的执行
		if(assignee != null)
		{
			runtimeService.setVariable(taskEntity.getExecutionId(), "batchAssigneeWorkgroupId", assignee.getWorkgroupId());
			runtimeService.setVariable(taskEntity.getExecutionId(), "batchAssigneeAccountId", assignee.getAccountId());
		}
		else
		{
			SysParam sysParam = sysParamService.getSysParamByParamName("noAssigneeTaskProcessAccountId");
			return sysParam.getParamValue();
		}
		return assignee.getAccountId();
	}

}
