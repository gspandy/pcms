package com.pujjr.jbpm.core.handle;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

public interface ITaskCompleteHandle {
	public void handle(TaskEntity taskEntity);
}
