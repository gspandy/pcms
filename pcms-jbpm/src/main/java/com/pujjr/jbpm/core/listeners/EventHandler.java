package com.pujjr.jbpm.core.listeners;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * Activit �¼�������
 * 
 * **/
public interface EventHandler 
{
	 /**
	  * �¼�������
	  * @param event
	  */
	 public void handle(ActivitiEvent event);
}
