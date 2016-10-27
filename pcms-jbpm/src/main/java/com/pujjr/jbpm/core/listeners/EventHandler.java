package com.pujjr.jbpm.core.listeners;

import org.activiti.engine.delegate.event.ActivitiEvent;

import com.pujjr.jbpm.core.exception.NoAssigneeException;

/**
 * Activit 事件处理器
 * 
 * **/
public interface EventHandler 
{
	 /**
	  * 事件处理器
	  * @param event
	 * @throws NoAssigneeException 
	  */
	 public void handle(ActivitiEvent event);
}
