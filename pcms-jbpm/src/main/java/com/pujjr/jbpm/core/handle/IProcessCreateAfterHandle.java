package com.pujjr.jbpm.core.handle;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import com.pujjr.jbpm.core.command.StartProcessCommand;

public interface IProcessCreateAfterHandle 
{
	public void handle(StartProcessCommand command,ProcessInstance processInstance);
}
