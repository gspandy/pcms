package com.pujjr.jbpm.core.listeners;

import org.activiti.engine.delegate.event.ActivitiEvent;

import com.pujjr.jbpm.core.exception.NoAssigneeException;

/**
 * Activit �¼�������
 * 
 * **/
public interface EventHandler 
{
	 /**
	  * �¼�������
	  * @param event
	 * @throws NoAssigneeException 
	  */
	 public void handle(ActivitiEvent event);
}
