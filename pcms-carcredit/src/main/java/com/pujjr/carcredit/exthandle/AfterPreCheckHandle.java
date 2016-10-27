package com.pujjr.carcredit.exthandle;

import java.util.Date;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.carcredit.domain.AutoAssigneeConfig;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.jbpm.core.handle.ITaskCompleteHandle;
import com.pujjr.utils.Utils;
@Service("AfterPreCheckHandle")
public class AfterPreCheckHandle implements ITaskCompleteHandle {

	@Autowired
	private ITaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	
	@Override
	public void handle(TaskEntity taskEntity) {
		// TODO Auto-generated method stub
		AutoAssigneeConfig config = taskService.getAutoAssigneeConfigInfo();
		Date curTime = new Date();
		if(config.getEnabled()==true&&Utils.compareDateTime(config.getStartTime(), curTime)>=0&&Utils.compareDateTime(curTime, config.getEndTime())>=0)
		{
			runtimeService.setVariable(taskEntity.getExecutionId(), "autoAssignee", true);
		}
		else
		{
			runtimeService.setVariable(taskEntity.getExecutionId(), "autoAssignee", false);
		}
		
	}

}
