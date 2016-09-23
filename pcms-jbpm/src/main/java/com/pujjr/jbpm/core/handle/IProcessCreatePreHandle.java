package com.pujjr.jbpm.core.handle;

import java.util.Map;

import com.pujjr.jbpm.core.command.StartProcessCommand;

public interface IProcessCreatePreHandle 
{
	public void handle(StartProcessCommand command);
}
