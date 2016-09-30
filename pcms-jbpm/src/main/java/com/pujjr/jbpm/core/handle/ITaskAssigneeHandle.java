package com.pujjr.jbpm.core.handle;

import java.util.Map;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

public interface ITaskAssigneeHandle 
{
	public String handle(String assigneeParam,TaskEntity taskEntity);
}
