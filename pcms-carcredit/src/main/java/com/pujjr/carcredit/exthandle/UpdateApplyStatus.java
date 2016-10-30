package com.pujjr.carcredit.exthandle;

import java.util.Date;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.carcredit.constant.ApplyStatus;
import com.pujjr.carcredit.constant.TaskDefKey;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.core.handle.ITaskCreatePreHandle;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
@Service("UpdateApplyStatus")
/**
 * 功能：在任务创建时更新申请单状态
 * 参数：taskEntity 流程任务实体信息
 * ***/
public class UpdateApplyStatus implements ITaskCreatePreHandle {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IApplyService applySerivce;
	@Autowired
	private IRunPathService runPathService;
	
	@Override
	public void handle(TaskEntity taskEntity) {
		// TODO Auto-generated method stub
		//获取流程关联申请单信息
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult().getBusinessKey();
		//获取申请单信息
		Apply apply = applySerivce.getApply(businessKey);
		//获取任务定义KEY
		String taskDefKey = taskEntity.getTaskDefinitionKey();
		WorkflowRunPath runPath = runPathService.getFarestRunPathByActId(taskEntity.getProcessInstanceId(), taskDefKey);
		switch(taskDefKey)
		{
			//进入录入申请单节点为退回时，状态为审核退回
			case TaskDefKey.APPLY:
				if(runPath.getInJumpType().equals(CommandType.BACK.name()))
				{
					apply.setStatus(ApplyStatus.CHECK_BACK);
				}
				break;
			//进入初审或审核节点时，申请状态为待审核	
			case TaskDefKey.PRE_CHECK:
			case TaskDefKey.CHECK:
				apply.setStatus(ApplyStatus.WAIT_CHECK);
				break;
			//进入一级审批节点时：申请单状态为待审批
			case TaskDefKey.LVL1_APPROVE:
				apply.setStatus(ApplyStatus.WAIT_APPROVE);
				break;
			//进入签约节点时，如果是审批过来的申请单状态为审批通过，并记录批核时间
			case TaskDefKey.SIGN:
				apply.setStatus(ApplyStatus.WAIT_SIGN);
				//获取任务上级节点
				WorkflowRunPath parentRunPath = runPathService.getRunPathById(runPath.getParentUsertaskPathId());
				if(parentRunPath.getActId().equals(TaskDefKey.LVL1_APPROVE)||parentRunPath.getActId().equals(TaskDefKey.LVL2_APPROVE)||parentRunPath.getActId().equals(TaskDefKey.TEAN_APPROVE))
				{
					apply.setApproveDate(new Date());
				}
				break;
			//进入批核签约回访节点时，申请单状态为待放款审批
			case TaskDefKey.LOAN_CHECK_CALLBACK:
				apply.setStatus(ApplyStatus.WAIT_LOAN_CHECK);
				break;
			//进入放款节点时，申请单状态为待放款
			case TaskDefKey.LOAN:
				apply.setStatus(ApplyStatus.WAIT_LOAN);
				break;
			//进入复议申请节点时，申请单状态为审批拒绝
			case TaskDefKey.APPLY_RECONSIDER:
				apply.setStatus(ApplyStatus.APPROVE_REFUSE);
				//这里都是被拒绝的请求，应记录审批拒绝时间
				apply.setRefuseDate(new Date());
				break;
			//进入复核审核节点时，申请单状态为待复议审核
			case TaskDefKey.CHECK_RECONSIDER:
				apply.setStatus(ApplyStatus.WAIT_RECONSIDER);
				break;
			//进入变更申请复核节点时，申请单状态为待变更审批
			case TaskDefKey.CHECK_CHANGE_APPLY:
				apply.setStatus(ApplyStatus.WAIT_CHANGE_APPROVE);
				break;
			//进入取消申请复核节点时，社情的状态为待取消审批
			case TaskDefKey.CHECK_CANCEL_APPLY:
				apply.setStatus(ApplyStatus.WAIT_CANCEL_APPRVOE);
				break;
		}
		applySerivce.updateApply(apply);
	}

}
