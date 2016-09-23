package com.pujjr.jbpm.core.listeners;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * Activit 事件处理器
 * 
 * **/
public interface EventHandler 
{
	 /**
	  * 事件处理器
	  * @param event
	  */
	 public void handle(ActivitiEvent event);
}
