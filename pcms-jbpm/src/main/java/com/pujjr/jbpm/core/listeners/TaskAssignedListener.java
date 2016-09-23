package com.pujjr.jbpm.core.listeners;

import org.activiti.engine.delegate.event.ActivitiEvent;

public class TaskAssignedListener implements EventHandler {

	public void handle(ActivitiEvent event) 
	{
		// TODO Auto-generated method stub
		System.out.println("task Aassignee");
	}

}
