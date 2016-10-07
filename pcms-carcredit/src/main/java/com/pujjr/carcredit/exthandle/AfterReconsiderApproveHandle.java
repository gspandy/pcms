package com.pujjr.carcredit.exthandle;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.jbpm.core.handle.ITaskCompleteHandle;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
@Service("AfterReconsiderApproveHandle")
public class AfterReconsiderApproveHandle implements ITaskCompleteHandle {

	@Autowired
	private IRunPathService runPathService;
	@Override
	public void handle(TaskEntity taskEntity) {
		// TODO Auto-generated method stub
		//获取审核任务最后一次执行的路径信息
		WorkflowRunPath chechTaskRunPath = runPathService.getFarestRunPathByActId(taskEntity.getProcessInstanceId(), "zlsh");
		taskEntity.getExecution().setVariable("checkAssignee", chechTaskRunPath.getAssignee());
	}

}
