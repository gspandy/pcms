package com.pujjr.jbpm.core;

import com.pujjr.jbpm.core.command.AbstractExecutionCommand;
import com.pujjr.jbpm.domain.WorkflowRunPath;

public class ProcessHandleHelper 
{
	private static ThreadLocal<AbstractExecutionCommand> processCommandThreadLocal = new ThreadLocal<AbstractExecutionCommand>();

	private static ThreadLocal<WorkflowRunPath> backNodeThreadLocal = new ThreadLocal<WorkflowRunPath>();
	
	public static AbstractExecutionCommand getProcessCommand() {
		return processCommandThreadLocal.get();
	}

	public static void setProcessCommand(AbstractExecutionCommand processCommand) {
		processCommandThreadLocal.set(processCommand);
	}
	
	public static WorkflowRunPath getBackNode()
	{
		return backNodeThreadLocal.get();
	}
	
	public static void setBackNode(WorkflowRunPath backNode)
	{
		backNodeThreadLocal.set(backNode);
	}
	
	public static void clearProcessCommand()
	{
		processCommandThreadLocal.remove();
	}
	
	public static void clearBackNode()
	{
		backNodeThreadLocal.remove();
	}
	
	
}
